package com.example.mppproject.Service;

import com.example.mppproject.Model.Image;
import com.example.mppproject.Model.Property;
import com.example.mppproject.Repository.AddressRepository;
import com.example.mppproject.Repository.HomePropertyRepository;
import com.example.mppproject.Repository.ImageRepository;
import com.example.mppproject.Repository.PropertyRepository;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import com.google.auth.oauth2.GoogleCredentials;

import static com.example.mppproject.Config.Constants.*;

@Service
public class PropertyService {

    private final AddressRepository addressRepository;
    private final PropertyRepository propertyRepository;
    private final HomePropertyRepository homePropertyRepository;
    private final ImageRepository imageRepository;

    public PropertyService(AddressRepository addressRepository, PropertyRepository propertyRepository, HomePropertyRepository homePropertyRepository, ImageRepository imageRepository) {
        this.addressRepository = addressRepository;
        this.propertyRepository = propertyRepository;
        this.homePropertyRepository = homePropertyRepository;
        this.imageRepository = imageRepository;
    }

    @Transactional
    public Boolean create(Property property, List<MultipartFile> images) {
        List<Image> imagesArray = new ArrayList<>();
        try {
            for (int i = 0; i < images.size(); i++) {
                String objectName = generateFileName(images.get(i));

                Path JSONFilePath = ResourceUtils.getFile(FIREBASE_SDK_JSON).toPath();
                FileInputStream serviceAccount = new FileInputStream(String.valueOf(JSONFilePath));
                File file = convertMultiPartToFile(images.get(i));
                Path filePath = file.toPath();

                Storage storage = StorageOptions.newBuilder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).setProjectId(FIREBASE_PROJECT_ID).build().getService();
                BlobId blobId = BlobId.of(FIREBASE_BUCKET, objectName);
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(images.get(i).getContentType()).build();
                String extenstion = blobInfo.getContentType().split("/")[1];

//                System.out.println("https://firebasestorage.googleapis.com/v0/b/"+FIREBASE_BUCKET+"/o/"+objectName+"?alt=media&token=e7941d9c-0822-445e-8fc5-137b03c1c2b8");
                Blob b= storage.create(blobInfo, Files.readAllBytes(filePath));

                String imageUrl = "https://firebasestorage.googleapis.com/v0/b/"+
                        FIREBASE_BUCKET+"/o/"+
                        objectName+"?alt=media&token=e7941d9c-0822-445e-8fc5-137b03c1c2b8";

                Image image = new Image(imageUrl, extenstion, blobInfo.getName());
                imagesArray.add(image);
                file.delete();
            }
        }catch (IOException ioException){
            System.out.println(ioException.getMessage());
            return false;
        }
        addressRepository.save(property.getAddress());
        homePropertyRepository.save(property.getHomeProperty());
        propertyRepository.save(property);
        for(int i=0;i<imagesArray.size();i++){
            imagesArray.get(i).setProperty(property);
            imageRepository.save(imagesArray.get(i));
        }
        return true;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }
    public List<Property> getProperty() {
        return propertyRepository.findAll();
    }

    public Property getPropertyById(long id) {
        boolean exist = propertyRepository.existsById(id);
        if (!exist){
            throw new IllegalStateException("student " + id + "does not exist");
        }
        return  propertyRepository.findById(id);
    }

}
