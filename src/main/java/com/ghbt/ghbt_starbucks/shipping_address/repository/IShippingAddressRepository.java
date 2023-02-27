package com.ghbt.ghbt_starbucks.shipping_address.repository;

import com.ghbt.ghbt_starbucks.shipping_address.model.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {

}
