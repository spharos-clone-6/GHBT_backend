package com.ghbt.ghbt_starbucks.api.cart.repository;

import com.ghbt.ghbt_starbucks.api.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUser_Id(Long userid);

    //and (:uid is null or u.id = :uid) and (:pid is null or p.id = :pid)
    Cart findByUserIdAndProductId(Long userId,Long productId);

    public interface FindOneCartId {
        Long getId();
        Long getPid();
        Long getUid();
    }

}
