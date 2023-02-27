package com.ghbt.ghbt_starbucks.shipping_address.repository;

import com.ghbt.ghbt_starbucks.shipping_address.model.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {

}
