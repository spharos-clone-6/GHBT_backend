package com.ghbt.ghbt_starbucks.api.user.model;

import com.ghbt.ghbt_starbucks.global.security.dto.SignupDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ghbt.ghbt_starbucks.global.utility.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "is_agree")
    private Boolean isAgree;

    @Column(name = "shipping_address_agreement")
    private Boolean shippingAddressAgreement;

    @Column(name = "star")
    private Integer star;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "reward")
    private Boolean reward;

    @Column(name = "cash_recipe")
    private String cashRecipe;

    @Column(name = "business_recipe")
    private String businessRecipe;

    @Enumerated(EnumType.STRING)
    @Column(name = "ad_agreement")
    private AdAgreement adAgreement;

    //==편의 메서드==//
    //최초 회원가입시 들어가는 정보
    public static User signinUser(SignupDto signupDto) {
        return new UserBuilder()
            .email(signupDto.getEmail())
            .password(signupDto.getPassword())
            .role(Role.USER)
            .adAgreement(AdAgreement.toEnumByKey(signupDto.getAdAgreement()))
            .build();
    }

    //유저 정보 수정
    public Long changeNickName(String nickName) {
        this.nickName = nickName;
        return getId();
    }

    public void changeShippingAddressAgreement(boolean isCheck) {
        this.shippingAddressAgreement = isCheck;
    }
}
