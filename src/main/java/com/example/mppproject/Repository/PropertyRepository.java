package com.example.mppproject.Repository;

import com.example.mppproject.Model.Enum.ApprovedStatus;
import com.example.mppproject.Model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    Property findById(long id);

    boolean existsById(long id);

//    List<Property> findByIdAndApprovedStatus(Long id, ApprovedStatus approvedStatus);
//
//    List<Property> findByAppUser(long appUserId);
//
//    List<Property> findBytitle(String best);

    List<Property> findByCapacity(int i);


}