package com.ghbt.ghbt_starbucks.api.cart.repository;

import com.ghbt.ghbt_starbucks.api.cart.model.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "SELECT c FROM Cart c JOIN fetch c.user u  JOIN fetch c.product p LEFT JOIN SearchCategory sc on p.id = sc.productId.id Where sc.bigType != '케이크' and c.deleted = false and u.id = :userId")
    List<Cart> findAllByUser_IdAndDeleted(@Param("userId") Long userId);

    @Query(value = "SELECT c FROM Cart c JOIN fetch c.user u  JOIN fetch c.product p LEFT JOIN SearchCategory sc on p.id = sc.productId.id Where sc.bigType = '케이크' and c.deleted = false and u.id = :userId")
    List<Cart> findAllByUser_IdAndIceAndDeleted(@Param("userId") Long userId);

    //and (:uid is null or u.id = :uid) and (:pid is null or p.id = :pid)

    @Query(value = "SELECT c FROM Cart c LEFT JOIN c.user u LEFT JOIN c.product p WHERE p.id = :productId and u.id = :userId")
    Cart findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    @Query(value = " SELECT c FROM Cart c join fetch c.user u join fetch c.product p WHERE c.id = :cartId" )
    Optional<Cart> findByCartId(@Param("cartId") Long cartId);


}
