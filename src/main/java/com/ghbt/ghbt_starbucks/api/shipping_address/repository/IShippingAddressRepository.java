package com.ghbt.ghbt_starbucks.api.shipping_address.repository;

import com.ghbt.ghbt_starbucks.api.shipping_address.model.ShippingAddress;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {

    List<ShippingAddress> findAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}
