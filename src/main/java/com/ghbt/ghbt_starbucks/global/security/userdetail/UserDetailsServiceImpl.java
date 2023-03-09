package com.ghbt.ghbt_starbucks.global.security.userdetail;

import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final IUserRepository IUserRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User findUser = IUserRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("해당 이메일은 회원 가입이 되어있지 않습니다. -> " + email));

    if (findUser != null) {
      UserDetailsImpl userDetails = new UserDetailsImpl(findUser);
      return userDetails;
    }
    return null;
  }
}
