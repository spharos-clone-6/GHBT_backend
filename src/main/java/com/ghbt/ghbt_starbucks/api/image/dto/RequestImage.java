package com.ghbt.ghbt_starbucks.api.image.dto;

import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestImage {
    private Long productId;
    private String url;
}
