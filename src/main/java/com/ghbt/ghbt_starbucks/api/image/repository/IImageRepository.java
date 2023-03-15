package com.ghbt.ghbt_starbucks.api.image.repository;

import com.ghbt.ghbt_starbucks.api.image.model.Image;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IImageRepository extends JpaRepository<Image, Long> {

    List<Image> findALLByProductId(Long productId);
}
