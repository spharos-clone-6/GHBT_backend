package com.ghbt.ghbt_starbucks.api.purchase.repository;

import com.ghbt.ghbt_starbucks.api.purchase.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IPurchaseRepository extends JpaRepository<Purchase, Long>{

   List<Purchase> findAllByUserId(Long userId);

}
