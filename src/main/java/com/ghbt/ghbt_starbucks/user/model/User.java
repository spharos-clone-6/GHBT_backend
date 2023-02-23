package com.ghbt.ghbt_starbucks.user.model;

import com.ghbt.ghbt_starbucks.shippingaddress.model.ShippingAddress;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany
  private List<ShippingAddress> shippingAddressList;

  @Column(name = "name", nullable = false, length = 255)
  private String name;

  @Column(name = "user_id", nullable = false, length = 255)
  private String userId;

  @Column(name = "password", nullable = false, length = 255)
  private String password;

  @Column(name = "phone_number", nullable = false, length = 255)
  private String phoneNumber;

  @Column(name = "email", nullable = false, length = 255)
  private String email;

  @Column(name = "is_agree", nullable = false)
  private Boolean isAgree;

  @Column(name = "star")
  private Integer star;

  @Column(name = "nick_name", nullable = false)
  private String nickName;

  @Column(name = "gender", nullable = false)
  private Boolean gender;

  @Column(name = "reward", nullable = false)
  private Boolean reward;

  @Column(name = "cash_recipe")
  private String cashRecipe;

  @Column(name = "business_recipe")
  private String businessRecipe;
}
