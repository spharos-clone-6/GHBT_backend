package com.ghbt.ghbt_starbucks.api.image.service;

import com.ghbt.ghbt_starbucks.api.image.dto.RequestImage;
import com.ghbt.ghbt_starbucks.api.image.dto.ResponseImage;
import java.util.List;

public interface IImageService {

    void addImage(RequestImage requestImage);

    List<ResponseImage> getImageAll();


}
