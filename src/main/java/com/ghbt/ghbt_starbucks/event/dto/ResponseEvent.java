package com.ghbt.ghbt_starbucks.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEvent {
    private Long id;
    private String name;
    private String description;
    private String thumbnailUrl;
    private String descriptionUrl;
}
