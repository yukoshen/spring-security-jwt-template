# spring-security-jwt-template
This repository serves as a code template if you want to implement spring security that using is JWT

**Step by Step implementation:**

1. Go to https://start.spring.io/ and create a new springboot application. For this project I am using Springboot version 2.7.12, Java 8 and Maven
2. Add the necessary dependencies (_Spring Security is required_)
3. Create you SecurityConfigurer class and annotate it with @Configuration and @EnableWebSecurity
4. In your SecurityConfigurer class create bean _UserDetailsService, PasswordEncoder, SecurityFilterChain and AuthenticationProvider_<br>
* **UserDetailsService** - Bean to create a hard-coded user by using InMemoryUserDetailsManager or create user from database
* **PasswordEncoder** - Bean to encode your password 
* **SecurityFilterChain** - Bean to authorize Http requests
* **AuthenticationProvider** - Bean to create an authentication provider for your database



