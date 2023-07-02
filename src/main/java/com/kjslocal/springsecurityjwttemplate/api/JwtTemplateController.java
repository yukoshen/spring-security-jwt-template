package com.kjslocal.springsecurityjwttemplate.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kjslocal.springsecurityjwttemplate.dto.UserInfoRequestDto;
import com.kjslocal.springsecurityjwttemplate.dto.UserInfoResponseDto;
import com.kjslocal.springsecurityjwttemplate.security.model.AuthorizationRequest;
import com.kjslocal.springsecurityjwttemplate.security.service.JwtService;
import com.kjslocal.springsecurityjwttemplate.service.UserInfoService;

@RestController
@RequestMapping("/auth")
public class JwtTemplateController {

	@Autowired
	private UserInfoService service;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authManager;

	@GetMapping("/welcome")
	public String welcome() {
		return "This page is not secured";
	}

	@PostMapping(value = "/createUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoResponseDto> createUser(@RequestBody UserInfoRequestDto request) {
		return new ResponseEntity<>(service.createUser(request), HttpStatus.OK);
	}

	@GetMapping("/view/admin")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String goToAdminPage() {
		return "Hello Admin!";
	}

	@GetMapping("/view/user")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String goToUserPage() {
		return "Hello User!";
	}

	@PostMapping("/getToken")
	public String authenticateAndGetToken(@RequestBody AuthorizationRequest request) {
		
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())); 
		
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(request.getUserName());
		} else {
			throw new UsernameNotFoundException("invalid request");
		}


		
	}
}
