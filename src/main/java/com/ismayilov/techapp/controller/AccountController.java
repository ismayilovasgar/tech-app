package com.ismayilov.techapp.controller;

import com.ismayilov.techapp.dto.request.AccountToAccountRequestDTO;
import com.ismayilov.techapp.service.impl.AccountServiceImpl;
import com.ismayilov.techapp.service.inter.AccountService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountController {

    @Autowired
    AccountServiceImpl accountService;

    @GetMapping("/account")
    public ResponseEntity<?> getAccount() {
        return new ResponseEntity<>(accountService.getAccount(), HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody AccountToAccountRequestDTO accountToAccountRequestDTO) {
        return new ResponseEntity<>(accountService.account2account(accountToAccountRequestDTO), HttpStatus.OK);
    }
}
