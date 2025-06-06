package com.example.jobportal.config;

import com.example.jobportal.service.CustomUserDetailsService; // Import CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService; // Autowire your custom UserDetailsService

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // This bean is necessary to configure the AuthenticationManager with our UserDetailsService
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests(authorize -> authorize
                .antMatchers("/api/auth/register", "/h2-console/**", "/swagger-ui/**", "/v3/api-docs/**", "/css/**", "/js/**", "/login").permitAll() // Permit these
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // Specify custom login page URL if you have one, otherwise Spring provides a default
                .loginProcessingUrl("/login") // URL to submit the username and password to
                .defaultSuccessUrl("/api/jobs", true) // Redirect to /api/jobs on successful login
                .failureUrl("/login?error=true") // Redirect on login failure
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // URL to trigger logout
                .logoutSuccessUrl("/login?logout=true") // Redirect after logout
                .invalidateHttpSession(true) // Invalidate session
                .deleteCookies("JSESSIONID") // Delete cookies
                .permitAll()
            )
            .headers(headers -> headers.frameOptions().sameOrigin()); // For H2 console

        return http.build();
    }
}
