package com.ghbt.ghbt_starbucks.api.user.repository;

import com.ghbt.ghbt_starbucks.api.user.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
