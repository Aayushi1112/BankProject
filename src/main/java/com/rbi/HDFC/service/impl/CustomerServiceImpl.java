package com.rbi.HDFC.service.impl;

import com.rbi.HDFC.dto.*;
import com.rbi.HDFC.entity.AccountEntity;
import com.rbi.HDFC.entity.CustomerEntity;
import com.rbi.HDFC.entity.TransactionEntity;
import com.rbi.HDFC.exception.BusinessException;
import com.rbi.HDFC.exception.ErrorModel;
import com.rbi.HDFC.repository.AccountRepository;
import com.rbi.HDFC.repository.CustomerRepository;
import com.rbi.HDFC.repository.TransactionRepository;
import com.rbi.HDFC.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;


    @Override

    /**
     * @param customerRegDTO
     * Register a new customer check if customer with that adhaar already exist then do not register himand send error.
     *  If customer is present notify him that he already has an account with the bank
     *  copy values from CustomerRegisterDTO to the customerEntity
     *  and also do OnetoOne mapping with the account entity and save the customer in database
     *  next get the account row and set fields in that and save it and return
     *  the account created dto after setting values into it
     * @return
     */
    public AccountCreatedDTO register(CustomerRegisterDTO customerRegDTO) {
        Optional<CustomerEntity> customer = customerRepository.findByAdhaar(customerRegDTO.getAdhaar());
        AccountCreatedDTO accountCreatedDTO = new AccountCreatedDTO();
        List<ErrorModel> errors = null;
      /******/
        if (customer.isPresent()) {
            throw new BusinessException("Account with this adhaar already exists");
        }

        else
        {
            CustomerEntity customerEntity = new CustomerEntity();
            AccountEntity accentity = new AccountEntity();
            customerEntity.setAccount(accentity);
            BeanUtils.copyProperties(customerRegDTO, customerEntity);
            customerEntity = customerRepository.save(customerEntity);
            Optional<CustomerEntity> customerSaved = customerRepository.findByAdhaar(customerRegDTO.getAdhaar());

            Optional<AccountEntity> accEntity = accountRepository.findById(customerSaved.get().getAccount().getAccountId());
            if (accEntity.isPresent()) {
                AccountEntity saveEn = accEntity.get();
                accEntity.get().setBalance(0.0);
                accEntity.get().setAccountNumber(customerSaved.get().getId() + 10000);
                accEntity.get().setPassword(customerSaved.get().getOwnerName() + "*HDFC");
                saveEn = accountRepository.save(saveEn);
                accountCreatedDTO.setCustomerId(customerSaved.get().getId());
                accountCreatedDTO.setAccountNo(saveEn.getAccountNumber());
                accountCreatedDTO.setPassword(customerSaved.get().getOwnerName() + "*HDFC");
                accountCreatedDTO.setUserName(customerSaved.get().getOwnerName());
                accountCreatedDTO.setIFSCcode("HDFC_112");
            }
            else{
                throw new BusinessException("Sorry no account found");
            }
        }
        return accountCreatedDTO;
        }


    /**
     * @param accountNumber
     * @param password
     * Finds in the account database if account number and password exist if exist
     * it retruns that you are welcome
     * otherwise says not a registered user
     * @return
     */
    @Override
    public AccountDTO login(Long accountNumber, String password) {
        AccountDTO accountDTO = new AccountDTO();
        Optional<AccountEntity> accountEntity = accountRepository.findByAccountNumberAndPassword(accountNumber, password);
        if (accountEntity.isPresent()) {
            accountDTO.setMessage("************Welcome To HDFC Bank*******");
            accountDTO.setAccountNumber(accountEntity.get().getAccountNumber());
            accountDTO.setBalance(accountEntity.get().getBalance());
        } else {

            throw new BusinessException("You are not authorized to login");
        }
        return accountDTO;
    }


}
