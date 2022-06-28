package com.example.mppproject.Repository;
import com.example.mppproject.Model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByAppUser_Id(long appUserId);

    List<Property> findByCapacity(int i);




}