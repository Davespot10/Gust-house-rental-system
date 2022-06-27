package com.example.mppproject.Model.seed;

import com.example.mppproject.Model.HomeProperty;
import com.example.mppproject.Repository.HomePropertyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class HomePropertySeed implements CommandLineRunner {
   private final HomePropertyRepository homePropertyRepository;
    public HomePropertySeed(HomePropertyRepository homePropertyRepository){
        this.homePropertyRepository = homePropertyRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        loadData();
    }
    private void loadData() {
        if (homePropertyRepository.count() == 0) {
            HomeProperty data1 = new HomeProperty(2, 3, 2, "Best Mountain View");
            HomeProperty data2 = new HomeProperty(3, 4, 2, "Best Mountain View");
            HomeProperty data3 = new HomeProperty(1, 1, 2, "Best Mountain View");
            HomeProperty data4 = new HomeProperty(2, 3, 2, "Best Mountain View");

            homePropertyRepository.save(data1);
            homePropertyRepository.save(data2);
            homePropertyRepository.save(data3);
            homePropertyRepository.save(data4);
        }
    }
}
