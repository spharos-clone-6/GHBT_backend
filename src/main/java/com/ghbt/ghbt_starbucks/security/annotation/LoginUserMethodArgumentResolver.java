package com.ghbt.ghbt_starbucks.security.annotation;

import com.ghbt.ghbt_starbucks.error.ServiceException;
import com.ghbt.ghbt_starbucks.user.model.User;
import com.ghbt.ghbt_starbucks.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

  private final IUserRepository iUserRepository;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(LoginUser.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User loginUser = iUserRepository.findByEmail(authentication.getName())
        .orElseThrow(() -> new ServiceException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    return loginUser;
  }
}
