package com.ghbt.ghbt_starbucks.api.event.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestEvent {

    private String name;
    private String description;
    private String thumbnailUrl;
    private String descriptionUrl;

    private String tag;
}
