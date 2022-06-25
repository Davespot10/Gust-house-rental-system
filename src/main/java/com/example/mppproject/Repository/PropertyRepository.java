package com.example.mppproject.Repository;

import com.example.mppproject.Model.Enum.ApprovedStatus;
import com.example.mppproject.Model.Enum.Type;
import com.example.mppproject.Model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    Property findById(long id);

    boolean existsById(long id);

    List<Property> findByIdAndApprovedStatus(Long id, ApprovedStatus approvedStatus);
}