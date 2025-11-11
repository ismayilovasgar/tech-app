package com.ismayilov.techapp.controller;

import com.ismayilov.techapp.dto.request.UserRequestDTO;
import com.ismayilov.techapp.dto.response.UserResponseDTO;
import com.ismayilov.techapp.service.impl.UserServiceImpl;
import com.ismayilov.techapp.service.inter.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO userRequestDTO) {

//?        Wildcard	         Adı                        Qaydası	                      İcazə verdiyi əməliyyat
//*        <?>	             Unbounded wildcard	        Naməlum tip	                  Yalnız oxumaq
//*        <? extends T>	 Upper bounded wildcard	    T və T-nin alt sinifləri	  Yalnız oxumaq
//*        <? super T>  	 Lower bounded wildcard	    T və T-nin üst sinifləri	  Yazmaq olar
        return new ResponseEntity<>(userService.saveUser(userRequestDTO), HttpStatus.OK);
    }

}
