package com.kjslocal.springsecurityjwttemplate.repository;

import com.kjslocal.springsecurityjwttemplate.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo, Integer> {
}
