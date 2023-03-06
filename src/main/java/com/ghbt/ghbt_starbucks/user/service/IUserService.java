package com.ghbt.ghbt_starbucks.user.service;

import com.ghbt.ghbt_starbucks.security.dto.SignupDto;

public interface IUserService {

  Long signupUser(SignupDto signupDto);
}
