package com.ghbt.ghbt_starbucks.api.user.dto;

import com.ghbt.ghbt_starbucks.api.user.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseUserDto {

    private Long id;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String email;
    private Boolean gender;
    private Integer star;
    private Boolean reward;
    private Boolean isAgree;
    private String cashRecipe;
    private String businessRecipe;


    public static ResponseUserDto from(User user) {
        return ResponseUserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .nickName(user.getNickName())
            .phoneNumber(user.getPhoneNumber())
            .email(user.getEmail())
            .gender(user.getGender())
            .star(user.getStar())
            .reward(user.getReward())
            .isAgree(user.getIsAgree())
            .cashRecipe(user.getCashRecipe())
            .businessRecipe(user.getBusinessRecipe())
            .build();
    }
}
