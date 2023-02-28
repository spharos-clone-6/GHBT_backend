package com.ghbt.ghbt_starbucks.cart.repository;

import com.ghbt.ghbt_starbucks.cart.model.Cart;
import com.ghbt.ghbt_starbucks.cart.vo.ResponseCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICartRepository extends JpaRepository<Cart, Long> {
    List<ResponseCart> findAllByUser_Id(Long userid);
}
