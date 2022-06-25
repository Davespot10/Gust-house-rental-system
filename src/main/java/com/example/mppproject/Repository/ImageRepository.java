package com.example.mppproject.Repository;

import com.example.mppproject.Model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProperty_Id(Long id);

}