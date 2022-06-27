package com.example.mppproject.Model.seed;

import com.example.mppproject.Model.*;
import com.example.mppproject.Model.Enum.ApprovedStatus;
import com.example.mppproject.Model.Enum.Space;
import com.example.mppproject.Model.Enum.Type;
import com.example.mppproject.Repository.*;
import org.hibernate.annotations.common.reflection.XProperty;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class PropertySeed implements CommandLineRunner {
    private final PropertyRepository propertyRepository;
    private final HomePropertyRepository homepropertyRepository;
    private final AppUserRepository appUserRepository;

    private final AddressRepository addressRepository;
    private final AccountRepository accountRepository;

    public PropertySeed(PropertyRepository propertyRepository,
                        HomePropertyRepository homepropertyRepository,
                        AppUserRepository appUserRepository,
                        AccountRepository accountRepository,
                        AddressRepository addressRepository){
        this.propertyRepository = propertyRepository;
        this.homepropertyRepository =homepropertyRepository;
        this.addressRepository =addressRepository;
        this.appUserRepository=appUserRepository;
        this.accountRepository=accountRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        loadData();
    }
    private void loadData() {
        if (propertyRepository.count() == 0) {

            Property property = new Property();
            property.setAvailabiltyStatus(true);
            property.setApprovedStatus(ApprovedStatus.APPROVED);
            property.setCapacity(3);
            HomeProperty homeProperty= new HomeProperty();
            homeProperty.setDescription("Relax with the whole family or " +
                    "a group of friends in this awesome Lake Ozark " +
                            "Vacation Home! Located on the 4MM Powerline Cove.");
            homeProperty.setBathRoomNumber(2);
            homeProperty.setBedNumber(3);
            homeProperty.setBedRoomNumber(2);
            property.setHomeProperty(homeProperty);
            property.setPricePerNight(450.0);
            property.setDescription("Relax with the whole family or " +
                    "a group of friends in this awesome Lake Ozark " +
                            "Vacation Home! Located on the 4MM Powerline Cove.");

            property.setSpace(Space.ENTIRE_PLACE);
            property.setTitle("Entire home hosted by Wende");
            Address address = new Address("Iowa", "Iowa City", "USA", "12354", "1000 N 4th Street", "41°24'12.2\"N", "2°10'26.5\"E");
            property.setAddress(address);
            Random rnd = new Random();
            int number = rnd.nextInt(99);
           String name = generateString(4);
            String email = name + number + "@gmail.com";
            AppUser appUser = new AppUser(name,email);

            number = rnd.nextInt(999999);

            Account account = new Account(number, 5000.0);
            appUser.setAccount(account);
            appUser.setAddress(address);
            property.setAppUser(appUser);
            property.setType(Type.HOME);

            accountRepository.save(account);
            homepropertyRepository.save(homeProperty);
            addressRepository.save(address);
            appUserRepository.save(appUser);

            propertyRepository.save(property);

            }





    }

    private String generateString(int n){

        String AlphaNumericString ="abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
