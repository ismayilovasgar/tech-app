package com.ismayilov.techapp.controller;

import com.ismayilov.techapp.service.impl.CurrencyServiceImpl;
import com.ismayilov.techapp.service.inter.CurrencyService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrencyController {

    @Autowired
    CurrencyServiceImpl currencyService;

    @RequestMapping("/currency")
    public ResponseEntity<?> getCurrencyMB() {
        return new ResponseEntity<>(currencyService.getCurrencyRate(), HttpStatus.OK);
    }
}
