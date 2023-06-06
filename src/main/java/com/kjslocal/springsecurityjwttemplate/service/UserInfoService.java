package com.kjslocal.springsecurityjwttemplate.service;

import com.kjslocal.springsecurityjwttemplate.dto.UserInfoRequestDto;
import com.kjslocal.springsecurityjwttemplate.dto.UserInfoResponseDto;

public interface UserInfoService {

    UserInfoResponseDto createUser(UserInfoRequestDto request);
}
