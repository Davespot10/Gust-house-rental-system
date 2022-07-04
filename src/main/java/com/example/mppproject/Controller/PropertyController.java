package com.example.mppproject.Controller;

import com.example.mppproject.Config.OurResponses;
import com.example.mppproject.Model.*;
import com.example.mppproject.Model.Enum.ApprovedStatus;
import com.example.mppproject.Model.Enum.Space;
import com.example.mppproject.Model.Enum.Type;
import com.example.mppproject.Service.PropertyService;
import com.example.mppproject.Service.ReviewService;
import com.example.mppproject.exceptionResponse.propertyException.PropertyBadRequestException;
import com.example.mppproject.exceptionResponse.propertyException.PropertyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/property")
public class PropertyController {

    private final PropertyService propertyService;
    private final ReviewService reviewService;

    @Autowired
    public PropertyController(PropertyService propertyService, ReviewService reviewService){
        this.propertyService = propertyService;
        this.reviewService = reviewService;
    }

    @CrossOrigin
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public HashMap<String, Object> createProperties(
                                                    //            user ID
                                                    @RequestPart(value = "user_id", required = true) String user_id,
                                                    @RequestPart("capacity") String capacity
                                                    ,@RequestPart("title") String title
                                                    ,@RequestPart("description") String description
                                                    ,@RequestPart("price_per_night") String price_per_night
                                                    ,@RequestPart("space") String space
                                                    ,@RequestPart("type") String type
//                                                    Address fields
                                                    ,@RequestPart("city") String city
                                                    ,@RequestPart("country") String country
                                                    ,@RequestPart(value = "lat", required = false) String lat
                                                    ,@RequestPart(value = "lon", required = false) String lon
                                                    ,@RequestPart(value = "state", required = false) String state
                                                    ,@RequestPart(value = "street_number", required = false) String street_number
                                                    ,@RequestPart(value = "zip_code", required = false) String zip_code
//                                                    Home property fields
                                                    ,@RequestPart("bath_room_number") String bath_room_number
                                                    ,@RequestPart("bed_number") String bed_number
                                                    ,@RequestPart("bed_room_number") String bed_room_number
                                                    ,@RequestPart("property_description") String property_description
//                                                    Image fields
                                                    ,@RequestPart("images") List<MultipartFile> images
    ) throws IOException {

        Address address = new Address(state,city,country,zip_code,street_number,lat,lon);
        HomeProperty homeProperty = new HomeProperty(Integer.parseInt(bath_room_number), Integer.parseInt(bed_number), Integer.parseInt(bed_room_number), property_description);
        Property property = new Property(title, Type.valueOf(type), Space.valueOf(space),description,address
                ,Double.parseDouble(price_per_night),
                ApprovedStatus.APPROVED,
                true, Integer.parseInt(capacity), null, homeProperty,null);

        Property result = propertyService.create(property, images, user_id);
        HashMap<String, Object> resp = new HashMap<>();
        resp.put("property", result);
        return resp;
    }


    @GetMapping
    public List<Property> getProperty() {
        return propertyService.getProperty();
    }

    @GetMapping(path = "{id}")
    public HashMap<Object, Object> getPropertyById(@PathVariable("id") long id) {
        return propertyService.getPropertyById(id);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public HashMap<Object, Object> updateProperties(
//            Property Id
            @RequestPart(value = "property_id",required = true) String property_id
//            user ID
            ,@RequestPart(value = "user_id", required = true) String user_id
            ,@RequestPart(value = "capacity",required = false) String capacity
            ,@RequestPart(value = "title", required = false) String title
            ,@RequestPart(value = "description", required = false) String description
            ,@RequestPart(value = "price_per_night", required = false) String price_per_night
            ,@RequestPart(value = "space", required = false) String space
            ,@RequestPart(value = "type", required = false) String type
//                                                    Address fields
            ,@RequestPart(value = "city", required = false) String city
            ,@RequestPart(value = "country", required = false) String country
            ,@RequestPart(value = "lat", required = false) String lat
            ,@RequestPart(value = "lon", required = false) String lon
            ,@RequestPart(value = "state", required = false) String state
            ,@RequestPart(value = "street_number", required = false) String street_number
            ,@RequestPart(value = "zip_code", required = false) String zip_code
//                                                    Home property fields
            ,@RequestPart(value = "bath_room_number", required = false) String bath_room_number
            ,@RequestPart(value = "bed_number", required = false) String bed_number
            ,@RequestPart(value = "bed_room_number", required = false) String bed_room_number
            ,@RequestPart(value = "property_description", required = false) String property_description
//                                                    Image fields
            ,@RequestPart(value = "images", required = false) List<MultipartFile> images
    ) throws PropertyNotFoundException, PropertyBadRequestException, IOException {

        Address address = new Address(state,city,country,zip_code,street_number,lat,lon);
        HomeProperty homeProperty = new HomeProperty(Integer.parseInt(bath_room_number), Integer.parseInt(bed_number), Integer.parseInt(bed_room_number), description);
        Property property = new Property(title, Type.valueOf(type), Space.valueOf(space),description,address
                ,Double.parseDouble(price_per_night),
                ApprovedStatus.APPROVED,
                true, Integer.parseInt(capacity), null, homeProperty,null);
        property.setId(Long.valueOf(property_id));

        Object result = propertyService.update(property, images, user_id);

        return OurResponses.okResponse(result);
    }

    @GetMapping(value = "/getAllMyPropertyByUserId/{userId}")
    public List<Property> getAllMyPropertyByUserId(@PathVariable("userId") long userId) {
        return propertyService.getAllMyPropertyByUserId(userId);
    }

    @GetMapping(value = "/getOnlyOneOfMyProperty")
    @ResponseBody
    public Property getOnlyOneOfMyProperty(@RequestParam Long propertyId, @RequestParam Long userId) {
        return propertyService.getOnlyOneOfMyProperty(propertyId, userId);
    }

    @GetMapping("/{propertyId}/reviews")
    public ResponseEntity<List<Review> > getReviewsForProperty(@PathVariable("propertyId") Long propertyId){
        List<Review> newReview = reviewService.getReviewsForProperty(propertyId);
        return new ResponseEntity<>(newReview, HttpStatus.ACCEPTED);
    }
    @PostMapping(path = "/{propertyId}/{userId}/review")
    public ResponseEntity<Review> createReview( @PathVariable("propertyId") Long propertyId, @PathVariable("userId") Long userId, @RequestBody Review review) {
        Review newReview = reviewService.createReview(propertyId, userId, review);
        return new ResponseEntity<>(newReview, HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/review/{reviewId}")
    public ResponseEntity<Review> updateReview( @PathVariable("reviewId") Long reviewId,  @RequestBody Review review) {
        Review newReview = reviewService.updateReview(reviewId, review);
        return new ResponseEntity<>(review, HttpStatus.ACCEPTED);
    }
    @DeleteMapping(path = "/review/{reviewId}")
    public ResponseEntity<String> deleteReview( @PathVariable("reviewId") Long reviewId,  @RequestBody Review review) {
        String delReview = reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(delReview, HttpStatus.ACCEPTED);
    }

}
