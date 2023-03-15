package com.ghbt.ghbt_starbucks.api.image.controller;

import com.ghbt.ghbt_starbucks.api.image.dto.RequestImage;
import com.ghbt.ghbt_starbucks.api.image.dto.ResponseImage;
import com.ghbt.ghbt_starbucks.api.image.service.IImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "이미지")
@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {

    private final IImageService iImageService;

    @PostMapping()
    @Operation(summary = "이미지 생성", description = " productId(long) url(String) 입력해주세요.")
    public ResponseEntity<Void> addImage(@RequestBody RequestImage requestImage) {
        iImageService.addImage(requestImage);

        return ResponseEntity.status(HttpStatus.OK)
            .build();
    }

    @GetMapping("/{productId}")
    @Operation(summary = "이미지 productId로 호출", description = " productId(long) 입력해주세요.")
    public ResponseEntity<Result> getAllImageByProductId(@PathVariable Long productId) {
        List<ResponseImage> responseImageList = iImageService.getImageAll(productId);

        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result<>(responseImageList));
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {

        private T images;
    }
}
