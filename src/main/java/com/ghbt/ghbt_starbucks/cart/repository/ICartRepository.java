package com.ghbt.ghbt_starbucks.cart.repository;

import com.ghbt.ghbt_starbucks.cart.model.Cart;
import com.ghbt.ghbt_starbucks.cart.vo.FindOneCartId;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUser_Id(Long userid);

    @Query( value = "SELECT c.id from cart c left join `user` u on u.id = c.user_id left join product p on p.id = c.product_id where deleted = TRUE and u.id = :usId and p.id = :prId",nativeQuery = true)
    FindOneCartId findByDeletedId(@Param("usId") Long usId,@Param("prId") Long prId );
}
