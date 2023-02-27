package com.ghbt.ghbt_starbucks.user.repository;

import com.ghbt.ghbt_starbucks.user.model.User;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.aspectj.apache.bcel.classfile.Module.Open;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
}
