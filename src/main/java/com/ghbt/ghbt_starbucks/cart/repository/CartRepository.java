package com.ghbt.ghbt_starbucks.cart.repository;

import com.ghbt.ghbt_starbucks.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
