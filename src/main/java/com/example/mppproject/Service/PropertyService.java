package com.example.mppproject.Service;

import com.example.mppproject.Model.Address;
import com.example.mppproject.Model.HomeProperty;
import com.example.mppproject.Model.Property;
import com.example.mppproject.Repository.AddressRepository;
import com.example.mppproject.Repository.HomePropertyRepository;
import com.example.mppproject.Repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PropertyService {

    private final AddressRepository addressRepository;
    private final PropertyRepository propertyRepository;
    private final HomePropertyRepository homePropertyRepository;

    public PropertyService(AddressRepository addressRepository, PropertyRepository propertyRepository, HomePropertyRepository homePropertyRepository) {
        this.addressRepository = addressRepository;
        this.propertyRepository = propertyRepository;
        this.homePropertyRepository = homePropertyRepository;
    }

    public Boolean create(Property property, Address address, HomeProperty homeProperty) {
        addressRepository.save(address);
        homePropertyRepository.save(homeProperty);
        propertyRepository.save(property);
        return true;
    }
}
