package com.ghbt.ghbt_starbucks.shipping_address.repository;

import com.ghbt.ghbt_starbucks.shipping_address.model.ShippingAddress;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {

  Optional<ShippingAddress> findByUserIdAndIsDefault(Long userId, Boolean isDefault);

  List<ShippingAddress> findAllByUserId(Long userId);
}
