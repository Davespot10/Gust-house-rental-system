package com.example.mppproject.Repository;

import com.example.mppproject.Model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    Property findById(long id);

    boolean existsById(long id);
}