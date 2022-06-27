package com.example.mppproject.Service;

import com.example.mppproject.Model.*;
import com.example.mppproject.Repository.AppUserRepository;
import com.example.mppproject.Repository.PropertyRepository;
import com.example.mppproject.Repository.ReservationRepository;
import com.example.mppproject.Repository.ReviewRepository;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.el.PropertyNotFoundException;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PropertyRepository propertyRepository;
    private final AppUserRepository appUserRepository;

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                         AppUserRepository appUserRepository,
                         PropertyRepository propertyRepository,
                         ReservationRepository reservationRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
        this.appUserRepository =appUserRepository;
        this.reservationRepository=reservationRepository;
    }

    public Review createReview(Long id, Long userId, Review review) {
        Property property = propertyRepository.findById(id).stream().findFirst().orElse(null);
        if(property ==null)
            throw new PropertyNotFoundException("Property Not found For review");

        AppUser appUser = appUserRepository.findById(userId).stream().findFirst().orElse(null);

        if(reservationRepository.findFirstByPropertyAndAppUser(property,appUser).isEmpty()){
            throw new IllegalStateException("Can not review this property/ User did not reserved this property");
        }

        review.setAppUser(appUser);
        review.setProperty(property);

        reviewRepository.save(review);
        return review;
    }
}
