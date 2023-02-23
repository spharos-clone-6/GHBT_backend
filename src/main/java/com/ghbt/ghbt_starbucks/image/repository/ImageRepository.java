package com.ghbt.ghbt_starbucks.image.repository;

import com.ghbt.ghbt_starbucks.image.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
