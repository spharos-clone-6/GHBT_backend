package com.ghbt.ghbt_starbucks.shippingaddress.repository;

import com.ghbt.ghbt_starbucks.shippingaddress.model.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {

}
