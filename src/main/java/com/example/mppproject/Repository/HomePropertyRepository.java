package com.example.mppproject.Repository;

import com.example.mppproject.Model.HomeProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HomePropertyRepository extends JpaRepository<HomeProperty, Long> {
}