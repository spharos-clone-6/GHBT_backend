package com.ghbt.ghbt_starbucks.api.image.service;

import com.ghbt.ghbt_starbucks.api.image.dto.RequestImage;
import com.ghbt.ghbt_starbucks.api.image.dto.ResponseImage;
import com.ghbt.ghbt_starbucks.api.image.model.Image;
import com.ghbt.ghbt_starbucks.api.image.repository.IImageRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class ImageServiceImpl implements IImageService {

    private final IImageRepository iImageRepository;

    @Override
    public void addImage(RequestImage requestImage) {
        Image image = Image.builder()
            .url(requestImage.getUrl())
            .productId(requestImage.getProductId())
            .build();

        iImageRepository.save(image);
    }

    @Override
    public List<ResponseImage> getImageAll(Long productId) {
        List<Image> imageList = iImageRepository.findALLByProductId(productId);

        ModelMapper modelMapper = new ModelMapper();

        List<ResponseImage> responseImageList = new ArrayList<>();
        imageList.forEach(image -> {
            ResponseImage responseImage = modelMapper.map(image, ResponseImage.class);
            responseImageList.add(responseImage);
        });

        return responseImageList;
    }
}
