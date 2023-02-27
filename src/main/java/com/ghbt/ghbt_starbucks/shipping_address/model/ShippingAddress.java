package com.ghbt.ghbt_starbucks.shipping_address.model;

import com.ghbt.ghbt_starbucks.user.model.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.ghbt.ghbt_starbucks.utility.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ShippingAddress extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private User users;

  @Column(name = "receiver")
  private String receiver;

  @Column(name = "zip_code")
  private String zipCode;

  @Column(name = "address_nickname")
  private String addressNickname;

  @Column(name = "detail_address")
  private String detailAddress;

  @Column(name = "base_address")
  private String baseAddress;

  @Column(name = "phone_number_1")
  private String phoneNumber1;

  @Column(name = "phone_number_2")
  private String phoneNumber2;

  @Column(name = "notice")
  private String notice;

  @Column(name = "is_default")
  private Boolean isDefault;

}
