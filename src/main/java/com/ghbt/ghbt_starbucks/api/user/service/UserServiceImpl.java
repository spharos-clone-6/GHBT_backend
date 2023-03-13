package com.ghbt.ghbt_starbucks.api.user.service;

import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import com.ghbt.ghbt_starbucks.global.security.dto.SignupDto;
import com.ghbt.ghbt_starbucks.api.user.dto.UpdateUserDto;
import com.ghbt.ghbt_starbucks.api.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements IUserService {

    private final IUserRepository iUserRepository;

    @Override
    public User getUser(Long userId) {
        return iUserRepository.findById(userId)
            .orElseThrow(() -> new ServiceException("요청하신 유저를 찾을 수 없습니다.", HttpStatus.NO_CONTENT));
    }

    @Override
    @Transactional
    public Long updateUser(Long userId, UpdateUserDto updateUserDto) {
        User findUser = getUser(userId);
        return findUser.changeNickName(updateUserDto.getNickName());
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        iUserRepository.deleteById(userId);
    }
}
