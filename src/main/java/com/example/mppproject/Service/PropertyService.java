package com.example.mppproject.Service;

import com.example.mppproject.Model.AppUser;
import com.example.mppproject.Model.Enum.ApprovedStatus;
import com.example.mppproject.Model.Image;
import com.example.mppproject.Model.Property;
import com.example.mppproject.Repository.*;
import com.example.mppproject.exceptionResponse.propertyException.PropertyBadRequestException;
import com.example.mppproject.exceptionResponse.propertyException.PropertyNotFoundException;
import com.example.mppproject.exceptionResponse.userException.UserNotFoundException;
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
    private final AppUserRepository appUserRepository;

    public PropertyService(AddressRepository addressRepository, PropertyRepository propertyRepository, HomePropertyRepository homePropertyRepository, ImageRepository imageRepository, AppUserRepository appUserRepository) {
        this.addressRepository = addressRepository;
        this.propertyRepository = propertyRepository;
        this.homePropertyRepository = homePropertyRepository;
        this.imageRepository = imageRepository;
        this.appUserRepository = appUserRepository;
    }

    @Transactional
    public Property create(Property property, List<MultipartFile> images, String user_id) throws IOException {
        AppUser appUser= appUserRepository.findById(Long.parseLong(user_id));
        if(appUser == null){
            throw new UserNotFoundException("User not found");
        }
        property.setAppUser(appUser);
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
            throw new IOException();
        }
        addressRepository.save(property.getAddress());
        homePropertyRepository.save(property.getHomeProperty());
        propertyRepository.save(property);
        for(int i=0;i<imagesArray.size();i++){
            imagesArray.get(i).setProperty(property);
            imageRepository.save(imagesArray.get(i));
        }
        return property;
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
            throw new IllegalStateException("student id :" + id + " does not exist");
        }

        return  propertyRepository.findById(id);
    }

    public Property update(Property property, List<MultipartFile> images, String user_id) throws PropertyNotFoundException, IOException {
        AppUser appUser= appUserRepository.findById(Long.parseLong(user_id));
        if(appUser == null){
            throw new UserNotFoundException("User not found");
        }
        property.setAppUser(appUser);
        Optional<Property> p = propertyRepository.findById(property.getId());
        if(p.isEmpty()){
            throw new PropertyNotFoundException("Property with this Id not found");
        }
        Property p2 = p.get();

        List<Image> oldImages = imageRepository.findByProperty_Id(p2.getId());
        List<Long> oldId = new ArrayList<>();
        for(int i=0;i<oldImages.size();i++){
            oldId.add(oldImages.get(i).getId());
        }

        if(p2.getApprovedStatus().equals(ApprovedStatus.PENDING) ||
                p2.getApprovedStatus().equals(ApprovedStatus.DISAPPROVED)){
            p2.setAppUser(appUser);
            p2.setTitle(property.getTitle());
            p2.setType(property.getType());
            p2.setSpace(property.getSpace());
            p2.setDescription(property.getDescription());
            p2.setAddress(property.getAddress());
            p2.setPricePerNight(property.getPricePerNight());
            p2.setApprovedStatus(property.getApprovedStatus());
            p2.setAvailabiltyStatus(property.getAvailabiltyStatus());
            p2.setCapacity(property.getCapacity());
            p2.setReviews(null);
            p2.setReservations(null);
            p2.setHomeProperty(property.getHomeProperty());
            List<Image> imagesArray = new ArrayList<>();
            try {
                imageRepository.deleteAllById(oldId);
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
                    Blob b= storage.create(blobInfo, Files.readAllBytes(filePath));

                    String imageUrl = "https://firebasestorage.googleapis.com/v0/b/"+
                            FIREBASE_BUCKET+"/o/"+
                            objectName+"?alt=media&token=e7941d9c-0822-445e-8fc5-137b03c1c2b8";

                    Image image = new Image(imageUrl, extenstion, blobInfo.getName());
                    imagesArray.add(image);
                    file.delete();
                }
                for(int i=0;i<imagesArray.size();i++){
                    imagesArray.get(i).setProperty(p2);
                    imageRepository.save(imagesArray.get(i));
                }
            }catch (IOException ioException){
                System.out.println(ioException.getMessage());
                throw new IOException();
            }
            return p2;

        }
        throw new PropertyBadRequestException("You can not update the property");
    }

    public List<Property> getAllMyPropertyByUserId(long appUserId) {
        List<Property> listOfAppUserData = propertyRepository.findByAppUser_Id(appUserId);
        if (listOfAppUserData.size() == 0) {
            throw new UserNotFoundException("user does not exist");
        }
        return listOfAppUserData;
    }

        public Property getOnlyOneOfMyProperty(long propertyId, long userId) {
        List<Property> listOfAppUserData = getAllMyPropertyByUserId(userId);
        if (listOfAppUserData.size() == 0) {
            throw new UserNotFoundException("user does not exist");
        }
        Property property = new Property();
        for (Property property1 : listOfAppUserData) {
            if (property1.getId().equals(propertyId)) {
                property = property1;
                return property;
            }
        }

        if (property.getId() == null) {
            throw new UserNotFoundException("Property value does not exist");
        }
        return property;
    }

}
