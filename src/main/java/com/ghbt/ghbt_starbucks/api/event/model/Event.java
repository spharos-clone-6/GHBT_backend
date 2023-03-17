package com.ghbt.ghbt_starbucks.api.event.model;

import com.ghbt.ghbt_starbucks.global.utility.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "thumbnail_url", nullable = false, length = 255)
    private String thumbnailUrl;

    @Column(name = "description_url", nullable = false, length = 255)
    private String descriptionUrl;

    @Column(name = "tag")
    private String tag;
}
