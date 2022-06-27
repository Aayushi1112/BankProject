package com.rbi.HDFC.controller;

import com.rbi.HDFC.dto.*;
import com.rbi.HDFC.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
/*
Register a  new customer and then login him
*/

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @PostMapping("/register")
    public ResponseEntity<AccountCreatedDTO> register(@Valid @RequestBody CustomerRegisterDTO customerRegDTO){
        AccountCreatedDTO accountCreatedDTO = customerService.register(customerRegDTO);
        return new ResponseEntity<>(accountCreatedDTO, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<AccountDTO> login(@RequestBody AccountCreatedDTO accountCreatedDTO) {
        AccountDTO accountDTO = (customerService.login(accountCreatedDTO.getAccountNo(),accountCreatedDTO.getPassword()));
        ResponseEntity<AccountDTO> responseEntity = new ResponseEntity<>(accountDTO, HttpStatus.OK);
        return responseEntity;
    }
}
