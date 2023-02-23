package com.ghbt.ghbt_starbucks.user.repository;

import com.ghbt.ghbt_starbucks.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
