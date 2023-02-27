package com.ghbt.ghbt_starbucks.user.service;

import com.ghbt.ghbt_starbucks.security.dto.SignupDto;
import com.ghbt.ghbt_starbucks.user.model.User;
import com.ghbt.ghbt_starbucks.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public Long signupUser(SignupDto signupDto) {
    User user = User.signinUser(signupDto);
    User savedUser = userRepository.save(user);
    return savedUser.getId();
  }
}