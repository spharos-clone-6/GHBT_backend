package com.ghbt.ghbt_starbucks.api.cart.repository;

import com.ghbt.ghbt_starbucks.api.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUser_Id(Long userid);

    //and (:uid is null or u.id = :uid) and (:pid is null or p.id = :pid)

    @Query( value = "select c.id from cart c left join `user` u on u.id = c.user_id left join product p on p.id = c.product_id where deleted = false and p.id = :pid and u.id = :uid",nativeQuery = true)
    FindOneCartId findAllByDeletedId(@Param("uid") Long uid, @Param("pid") Long pid);

    public interface FindOneCartId {
        Long getId();
        Long getPid();
        Long getUid();
    }

}
