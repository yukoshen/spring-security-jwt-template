package com.kjslocal.springsecurityjwttemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kjslocal.springsecurityjwttemplate.entity.UserInfo;
import com.kjslocal.springsecurityjwttemplate.repository.UserInfoRepository;

@SpringBootApplication
public class SpringSecurityJwtTemplateApplication {

	@Autowired
	UserInfoRepository repository;

	@Autowired
	PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtTemplateApplication.class, args);
	}

	@PostConstruct
	public void insertUsers() {

		List<UserInfo> users = Stream.of(
				UserInfo.builder().name("admin").email("admin@gmail.com").password(encoder.encode("password"))
						.roles("ROLE_ADMIN").build(),
				UserInfo.builder().name("user").email("user@gmail.com").password(encoder.encode("password"))
						.roles("ROLE_USER").build())
				.collect(Collectors.toList());

		repository.saveAll(users);

	}

}
