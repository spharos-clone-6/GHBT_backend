package com.ghbt.ghbt_starbucks.user.model;

import com.ghbt.ghbt_starbucks.security.dto.SignupDto;
import com.ghbt.ghbt_starbucks.shippingaddress.model.ShippingAddress;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.ghbt.ghbt_starbucks.utility.BaseTimeEntity;
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

  @OneToMany
  private List<ShippingAddress> shippingAddressList;

  @Column(name = "name")
  private String name;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(name = "user_uuid")
  private String userUUID;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "is_agree")
  private Boolean isAgree;

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

  public static User signinUser(SignupDto signupDto) {
    return new UserBuilder()
        .email(signupDto.getEmail())
        .password(signupDto.getPassword())
        .role(Role.USER)
        .build();
  }
}
