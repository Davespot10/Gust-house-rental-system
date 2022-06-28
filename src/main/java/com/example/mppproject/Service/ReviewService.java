package com.example.mppproject.Service;

import com.example.mppproject.Model.*;
import com.example.mppproject.Repository.AppUserRepository;
import com.example.mppproject.Repository.PropertyRepository;
import com.example.mppproject.Repository.ReservationRepository;
import com.example.mppproject.Repository.ReviewRepository;
import com.example.mppproject.exceptionResponse.reviewException.ReviewNotFoundException;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.el.PropertyNotFoundException;
import java.util.List;
import java.util.Optional;

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

    public List<Review> getReviewsForProperty(Long propertyId) {

        return  reviewRepository.findAllByPropertyId(propertyId);

    }

    public Review updateReview(Long reviewId, Review review) {
        Optional<Review> opt = reviewRepository.findById(reviewId);
        if(opt.isPresent()){
            Review oldReview = opt.get();
            if(review.getReview() != null){ //and the current user is the owner of this review
                oldReview.setReview(review.getReview());
            }
            reviewRepository.save(oldReview);
            return oldReview;
        }else{
            throw new ReviewNotFoundException("Review not found for update");
        }

    }

    public String deleteReview(Long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if(review.isPresent()) { //&& the logg in user is the owner
            reviewRepository.delete(review.get());
        }else{
            throw new ReviewNotFoundException("Review not found for delete");
        }
      return "Successfully deleted review from the database";
    }
}

