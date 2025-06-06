package com.example.jobportal.controller;

import com.example.jobportal.dto.UserDto;
import com.example.jobportal.model.User;
import com.example.jobportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        try {
            User registeredUser = userService.registerNewUser(userDto);
            // Avoid returning the password in the response
            UserDto responseDto = new UserDto();
            responseDto.setUsername(registeredUser.getUsername());
            responseDto.setEmail(registeredUser.getEmail());
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Login endpoint will be handled by Spring Security by default if formLogin() or httpBasic() is configured.
    // If using JWT, a custom /login or /authenticate endpoint would be created here.
}
