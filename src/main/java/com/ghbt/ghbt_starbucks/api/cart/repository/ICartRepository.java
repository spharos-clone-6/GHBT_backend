package com.ghbt.ghbt_starbucks.api.cart.repository;

import com.ghbt.ghbt_starbucks.api.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUser_IdAndDeleted(Long userid, Boolean deleted);

    //and (:uid is null or u.id = :uid) and (:pid is null or p.id = :pid)

    @Query(value = "SELECT c FROM Cart c LEFT JOIN c.user u LEFT JOIN c.product p WHERE p.id = :productId and u.id = :userId")
    Cart findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

}
