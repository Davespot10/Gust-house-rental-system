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
//            Image data1 = new Image("Iowa", "Fairfiled", "USA", "52557", "1000 N 4th Street", "41°24'12.2\"N", "2°10'26.5\"E");
//            Image data2 = new Image("Addis Ababa", "Addis Ababa", "Ethiopia", "52557", "1000 N 4th Street", "41°24'12.2\"N", "2°10'26.5\"E");
//            Image data3 = new Image("Nairobi", "Nairobi", "Kenya", "12506", "1000 N 4th Street", "41°24'12.2\"N", "2°10'26.5\"E");
//            Image data4 = new Image("Iowa", "Iowa City", "USA", "12354", "1000 N 4th Street", "41°24'12.2\"N", "2°10'26.5\"E");
//
//            imageRepository.save(data1);
//            imageRepository.save(data2);
//            imageRepository.save(data3);
//            imageRepository.save(data4);
//        }
}
