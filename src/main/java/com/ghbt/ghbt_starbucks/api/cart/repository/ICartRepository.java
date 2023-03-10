package com.ghbt.ghbt_starbucks.api.cart.repository;

import com.ghbt.ghbt_starbucks.api.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUser_IdAndDeleted(Long userid,Boolean deleted);

    //and (:uid is null or u.id = :uid) and (:pid is null or p.id = :pid)
    Cart findByUserIdAndProductId(Long userId,Long productId);

}
