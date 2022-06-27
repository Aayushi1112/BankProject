package com.rbi.HDFC.controller;

import com.rbi.HDFC.dto.AccountCreatedDTO;
import com.rbi.HDFC.dto.AccountDTO;
import com.rbi.HDFC.dto.TransactionDTO;
import com.rbi.HDFC.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * Credit money in Account
 * Debit money from Account
 * Get my account details
 * Get all the transactions done by a user
 */

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/credit")
    public ResponseEntity<AccountDTO> credit( @RequestBody AccountDTO accountDTO){
        AccountDTO account=accountService.credit(accountDTO.getAccountNumber(),accountDTO.getAmount());
        ResponseEntity<AccountDTO> responseEntity = new ResponseEntity<>(account, HttpStatus.OK);
        return responseEntity;
    }
    @PostMapping("/debit")
    public ResponseEntity<AccountDTO> debit( @RequestBody AccountDTO accountDTO){
        AccountDTO account=accountService.debit(accountDTO.getAccountNumber(),accountDTO.getAmount());
        ResponseEntity<AccountDTO> responseEntity = new ResponseEntity<>(account, HttpStatus.OK);
        return responseEntity;
    }
    @GetMapping("/alltransactions/{accountNo}")
    public ResponseEntity<List<TransactionDTO>> getAllTransactionss(@PathVariable Long accountNo){
        List<TransactionDTO> transList=accountService.getAllTransactions(accountNo);
        ResponseEntity<List<TransactionDTO>> responseEntity = new ResponseEntity<>(transList, HttpStatus.OK);
        return responseEntity;
    }
    @GetMapping("/myAccount/{customerId}")
    public ResponseEntity<AccountCreatedDTO> myaccount(@PathVariable Long customerId){
        AccountCreatedDTO account=accountService.myaccount(customerId);
        ResponseEntity<AccountCreatedDTO> responseEntity = new ResponseEntity<>(account, HttpStatus.OK);
        return responseEntity;
    }
}
