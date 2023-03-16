package com.ghbt.ghbt_starbucks.api.shipping_address.model;

import com.ghbt.ghbt_starbucks.api.shipping_address.dto.RequestShippingAddress;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class ShippingAddress extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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

    //==편의메서드==//
    public void update(RequestShippingAddress requestUpdateShippingAddress) {

        this.receiver = requestUpdateShippingAddress.getReceiver();
        this.addressNickname = requestUpdateShippingAddress.getAddressNickname();
        this.baseAddress = requestUpdateShippingAddress.getBaseAddress();
        this.detailAddress = requestUpdateShippingAddress.getDetailAddress();
        this.zipCode = requestUpdateShippingAddress.getZipCode();
        this.phoneNumber1 = requestUpdateShippingAddress.getPhoneNumber1();
        this.phoneNumber2 = requestUpdateShippingAddress.getPhoneNumber2();
        this.notice = requestUpdateShippingAddress.getNotice();
        this.isDefault = requestUpdateShippingAddress.getIsDefault();
    }

    public void changeIsDefault() {
        this.isDefault = false;
    }
}
