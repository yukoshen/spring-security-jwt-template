package com.kjslocal.springsecurityjwttemplate.api;

import com.kjslocal.springsecurityjwttemplate.dto.UserInfoRequestDto;
import com.kjslocal.springsecurityjwttemplate.dto.UserInfoResponseDto;
import com.kjslocal.springsecurityjwttemplate.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class JwtTemplateController {

    @Autowired
    private UserInfoService service;

    @GetMapping("/welcome")
    public String welcome(){
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
}
