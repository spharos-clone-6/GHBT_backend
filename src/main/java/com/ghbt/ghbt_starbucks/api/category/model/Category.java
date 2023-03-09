package com.ghbt.ghbt_starbucks.api.category.model;

import com.ghbt.ghbt_starbucks.global.utility.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false)
    private String type;

}

