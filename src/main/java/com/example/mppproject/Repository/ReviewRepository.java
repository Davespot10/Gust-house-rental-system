package com.example.mppproject.Repository;

import com.example.mppproject.Model.AppUser;
import com.example.mppproject.Model.Property;

import com.example.mppproject.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findFirstByPropertyAndAppUser(Property property, AppUser appUser);

    List<Review> findAllByPropertyId(Long propertyId);
}
