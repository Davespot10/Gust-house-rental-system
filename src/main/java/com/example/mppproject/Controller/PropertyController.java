package com.example.mppproject.Controller;

import com.example.mppproject.Config.OurResponses;
import com.example.mppproject.Model.Address;
import com.example.mppproject.Model.Enum.ApprovedStatus;
import com.example.mppproject.Model.Enum.Space;
import com.example.mppproject.Model.Enum.Type;
import com.example.mppproject.Model.HomeProperty;
import com.example.mppproject.Model.Property;
import com.example.mppproject.Service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/property")
public class PropertyController {

    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService){
        this.propertyService = propertyService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public HashMap<Object, Object> createProperties(
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
                                                    ,@RequestPart("images") List<MultipartFile> images
    ){

        System.out.println(capacity);
        System.out.println(title);
        System.out.println(description);
        System.out.println(price_per_night);
        System.out.println(space);
        System.out.println(type);
        System.out.println(images.size());

        Address address = new Address(state,city,country,zip_code,street_number,lat,lon);
        HomeProperty homeProperty = new HomeProperty(Integer.parseInt(bath_room_number), Integer.parseInt(bed_number), Integer.parseInt(bed_room_number), description);
        Property property = new Property(title, Type.valueOf(type), Space.valueOf(space),description,address
                ,Double.parseDouble(price_per_night),
                ApprovedStatus.PENDING,
                false, Integer.parseInt(capacity), null, homeProperty,null);

        Boolean result = propertyService.create(property, address, homeProperty);

        return OurResponses.okResponse();
    }


    @GetMapping
    public List<Property> getStudent() {
        return propertyService.getStudent();
    }

    @GetMapping(path = "{id}")
    public Property getStudentById(@PathVariable("id") long id) {
        return propertyService.getStudentById(id);
    }
}
