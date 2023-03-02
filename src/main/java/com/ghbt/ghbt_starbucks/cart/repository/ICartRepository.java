package com.ghbt.ghbt_starbucks.cart.repository;

import com.ghbt.ghbt_starbucks.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUser_Id(Long userid);

    @Query( value = "SELECT id from cart where deleted = TRUE",nativeQuery = true)
    Cart findByDeletedId(Long id);

    Cart updateByDeletedColumn(Long id);
}
