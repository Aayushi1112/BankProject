package com.rbi.HDFC.service;

import com.rbi.HDFC.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {

    CustomerDTO register(CustomerDTO customerDTO);
    String login(String email, String password);
    AccountDTO credit(Long customerId, Double amount);


    AccountDTO debit(Long customerId, Double balance);

    List<TransactionDTO> getAllTransactions(Long customerId);

    List<CustomerDTO> addBeneficiary(BeneficiaryDTO beneficiaryDTO);

    String transferMoney(TransferDTO transferDTO);
}
