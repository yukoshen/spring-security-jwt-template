package com.kjslocal.springsecurityjwttemplate.service;

import com.kjslocal.springsecurityjwttemplate.dto.UserInfoRequestDto;
import com.kjslocal.springsecurityjwttemplate.dto.UserInfoResponseDto;
import com.kjslocal.springsecurityjwttemplate.entity.UserInfo;
import com.kjslocal.springsecurityjwttemplate.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements  UserInfoService{

    @Autowired
    UserInfoRepository repository;

    @Autowired
    PasswordEncoder encoder;
    @Override
    public UserInfoResponseDto createUser(UserInfoRequestDto request) {

        UserInfo userInfo = UserInfo.builder().name(request.getName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .roles(request.getRoles())
                .build();

        UserInfo createdUser = repository.save(userInfo);

        return UserInfoResponseDto.builder().userId(createdUser.getUserId())
                .name(createdUser.getName())
                .email(createdUser.getEmail())
                .password(createdUser.getPassword())
                .roles(createdUser.getRoles())
                .build();
    }
}
