package com.ismayilov.techapp.controller;

import com.ismayilov.techapp.dto.request.AuthenticationRequestDTO;
import com.ismayilov.techapp.dto.request.UserRequestDTO;
import com.ismayilov.techapp.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//* @RestController = @Controller + @ResponseBody
@RequestMapping("api/v1")
@RestController
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(userService.saveUser(userRequestDTO), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        return new ResponseEntity<>(userService.loginUser(authenticationRequestDTO), HttpStatus.OK);
    }
}
