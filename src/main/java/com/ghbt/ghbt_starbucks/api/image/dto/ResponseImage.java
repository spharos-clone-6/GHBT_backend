package com.ghbt.ghbt_starbucks.api.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseImage {

    private Long id;
    private Long productId;
    private String url;
}
