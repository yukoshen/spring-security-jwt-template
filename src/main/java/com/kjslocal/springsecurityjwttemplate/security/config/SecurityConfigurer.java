package com.kjslocal.springsecurityjwttemplate.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kjslocal.springsecurityjwttemplate.security.filter.JwtAuthFilter;
import com.kjslocal.springsecurityjwttemplate.security.service.UserInfoUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurer {
	
	@Autowired
	private JwtAuthFilter filter;

    @Bean
    public UserDetailsService userDetailsService() {

        /** //Hard coded users
         UserDetails admin = User.withUsername("admin")
         .password(pwEncoder.encode("password"))
         .roles("ADMIN")
         .build();

         UserDetails user = User.withUsername("user")
         .password(pwEncoder.encode("password"))
         .roles("USER")
         .build();

         return new InMemoryUserDetailsManager(admin, user);
         **/


        // Fetch user from database
        return new UserInfoUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/auth/welcome", "/auth/createUser", "auth/getToken").permitAll()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/auth/view/**").authenticated()
                .and()
                //.formLogin() comment out to enable use of jwt token to access different endpoints
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    	return config.getAuthenticationManager();
    }
}
