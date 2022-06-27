package com.rbi.HDFC.service;

import com.rbi.HDFC.dto.*;

import java.util.List;

public interface CustomerService {

    AccountCreatedDTO register(CustomerRegisterDTO customerRegDTO);
    AccountDTO login(Long accountNumber, String password);



}
