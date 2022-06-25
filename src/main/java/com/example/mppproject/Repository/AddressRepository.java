package com.example.mppproject.Repository;

import com.example.mppproject.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    boolean findById(Long id);
}