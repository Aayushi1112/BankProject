package com.rbi.HDFC.service;

import com.rbi.HDFC.dto.AccountCreatedDTO;
import com.rbi.HDFC.dto.AccountDTO;
import com.rbi.HDFC.dto.TransactionDTO;

import java.util.List;

public interface AccountService {
    AccountDTO credit(Long customerId, Double amount);


    AccountDTO debit(Long customerId, Double balance);

    List<TransactionDTO> getAllTransactions(Long customerId);

    AccountCreatedDTO myaccount(Long customerId);
}
