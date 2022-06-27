package com.example.mppproject.Model.seed;

import com.example.mppproject.Model.Address;
import com.example.mppproject.Repository.AddressRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AddressSeed implements CommandLineRunner {
    AddressRepository addressRepository;
    public AddressSeed(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    private void loadData() {
        if (addressRepository.count() == 0) {
            Address data1 = new Address("Iowa", "Fairfiled", "USA", "52557", "1000 N 4th Street", "41°24'12.2\"N", "2°10'26.5\"E");
            Address data2 = new Address("Addis Ababa", "Addis Ababa", "Ethiopia", "52557", "1000 N 4th Street", "41°24'12.2\"N", "2°10'26.5\"E");
            Address data3 = new Address("Nairobi", "Nairobi", "Kenya", "12506", "1000 N 4th Street", "41°24'12.2\"N", "2°10'26.5\"E");
            Address data4 = new Address("Iowa", "Iowa City", "USA", "12354", "1000 N 4th Street", "41°24'12.2\"N", "2°10'26.5\"E");

            addressRepository.save(data1);
            addressRepository.save(data2);
            addressRepository.save(data3);
            addressRepository.save(data4);
        }


    }
}
