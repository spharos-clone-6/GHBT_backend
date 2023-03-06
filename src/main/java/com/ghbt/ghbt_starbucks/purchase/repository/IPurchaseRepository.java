package com.ghbt.ghbt_starbucks.purchase.repository;

import com.ghbt.ghbt_starbucks.purchase.model.Purchase;
import com.ghbt.ghbt_starbucks.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IPurchaseRepository extends JpaRepository<Purchase, Long>{

   List<Purchase> findAllByUserId(Long userId);

}
