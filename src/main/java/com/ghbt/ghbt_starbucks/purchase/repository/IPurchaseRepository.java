package com.ghbt.ghbt_starbucks.purchase.repository;

import com.ghbt.ghbt_starbucks.purchase.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchaseRepository extends JpaRepository<Purchase, Long>{

}
