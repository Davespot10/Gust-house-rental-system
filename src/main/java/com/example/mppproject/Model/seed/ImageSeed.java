package com.example.mppproject.Model.seed;

import com.example.mppproject.Model.Address;
import com.example.mppproject.Model.Image;
import com.example.mppproject.Repository.ImageRepository;
import org.springframework.boot.CommandLineRunner;

public class ImageSeed implements CommandLineRunner {

    ImageRepository imageRepository;
    public ImageSeed (ImageRepository imageRepository){
        this.imageRepository=imageRepository;
    }
    @Override
    public void run(String... args) throws Exception {
//        loadData();
    }

//    private void loadData() {
//        if (imageRepository.count() == 0) {
//            Image data1 = new Image("data:image/jpeg;base64,/9j/4AAQSkZ", "jpeg", "data:image/jpeg");
//
//
//            imageRepository.save(data1);
//
//        }
//    }
}
