package com.example.mppproject.Repository;

import com.example.mppproject.Model.*;
import com.example.mppproject.Model.Enum.ApprovedStatus;
import com.example.mppproject.Model.Enum.RoleType;
import com.example.mppproject.Model.Enum.Space;
import com.example.mppproject.Model.Enum.Type;
import com.example.mppproject.Service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class ReviewRepositoryTest {
    @Autowired
    private ReviewService reviewService;
    @MockBean
    private ReviewRepository reviewRepository;
//    public Review(AppUser appUser, Property property, String review) {
//        this.appUser = appUser;
//        this.property = property;
//        this.review = review;
//    }


    @Test
    public void getReviewsForProperty_test(){
        Account account = new Account(123465898, 672.2);
        Address address = new Address("Addis Ababa", "Addis Ababa", "Ethiopia", "1234", "5678", "19.2", "38.9");
        Set<Role> role = new HashSet<>();
        role.add(new Role(RoleType.GUEST));
        AppUser appUser = new AppUser("Dawit", "Demelash", "davespot10", role, "123456", address, account);
        Type type = Type.HOME;
        Space space = Space.ENTIRE_PLACE;
        ApprovedStatus approvedStatus = ApprovedStatus.PENDING;
        HomeProperty homeProperty = new HomeProperty(2, 3, 3, "Excellent Condition");
        Property property = new Property("Luxury Apartment", type, space,
                "stay safe", address, 123.22, approvedStatus, true, 2, homeProperty, appUser);
        property.setId(2l);
        Review review = new Review(appUser,property,"Nice Home");
        review.setId(5l);
        Mockito.when(reviewRepository.findAllByPropertyId(property.getId())).thenReturn((Stream.of(review)).collect(Collectors.toList()));
        assertEquals(1,reviewService.getReviewsForProperty(property.getId()).size());


    }
}