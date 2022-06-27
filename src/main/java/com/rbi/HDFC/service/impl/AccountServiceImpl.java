package com.rbi.HDFC.service.impl;

import com.rbi.HDFC.dto.AccountCreatedDTO;
import com.rbi.HDFC.dto.AccountDTO;
import com.rbi.HDFC.dto.TransactionDTO;
import com.rbi.HDFC.entity.AccountEntity;
import com.rbi.HDFC.entity.CustomerEntity;
import com.rbi.HDFC.entity.TransactionEntity;
import com.rbi.HDFC.exception.BusinessException;
import com.rbi.HDFC.exception.ErrorModel;
import com.rbi.HDFC.repository.AccountRepository;
import com.rbi.HDFC.repository.CustomerRepository;
import com.rbi.HDFC.repository.TransactionRepository;
import com.rbi.HDFC.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;

    /***
     *
     * @param accountNo
     * @param
     * accountNo take account no and check in the database if amount<0 then do not submit
     *           else take the maount and credit to the user balance
     *           2.Add this transaction to the transaction table as a credit transaction
     * @return
     */
    @Override
    public AccountDTO credit(Long accountNo, Double amount) {
        if (amount < 0) {
            ErrorModel model = new ErrorModel();
            model.setMessage("Amount cannot be less than 0");
            model.setCode("AMOUNT_001");
            List<ErrorModel> errors = new ArrayList<>();
            errors.add(model);
            throw new BusinessException(errors);
        }
        AccountDTO accountDTO = new AccountDTO();
        Optional<AccountEntity> accEntity = accountRepository.findByAccountNumber(accountNo);
        if (accEntity.isPresent()) {
            AccountEntity accountEntity = accEntity.get();
            Double balance = accountEntity.getBalance();
            Double newBalance = balance + amount;
            accountEntity.setBalance(newBalance);
            accountEntity = accountRepository.save(accountEntity);
            BeanUtils.copyProperties(accountEntity, accountDTO);
            accountDTO.setMessage("Credited : " + amount + " Rupees to your account, Now your balance is:  " + accountEntity.getBalance() + " Rupees");
            accountDTO.setAmount(null);
            addTransaction(amount, accountEntity,"Credit");
        } else {
            ErrorModel model = new ErrorModel();
            model.setMessage("Sorry no account found");
            model.setCode("ACCOUNT_001");
            List<ErrorModel> errors = new ArrayList<>();
            errors.add(model);
            throw new BusinessException(errors);

        }


        return accountDTO;
    }

    /***
     *
     * @param accountNo
     * @param amount 1.take the account no and the amount to be debited from account
     *               if the amount <0 notify the customer
     *               if balance<=0 or amount>balance then notify the customer of insufficient balance
     *               else debit from the account and add transaction as debited.
     * @return
     */
    @Override
    public AccountDTO debit(Long accountNo, Double amount) {
        if (amount < 0) {
            ErrorModel model = new ErrorModel();
            model.setMessage("Amount cannot be less than 0");
            model.setCode("AMOUNT_001");
            List<ErrorModel> errors = new ArrayList<>();
            errors.add(model);
            throw new BusinessException(errors);
        }
        AccountDTO accountDTO = new AccountDTO();
        Optional<AccountEntity> accEntity = accountRepository.findByAccountNumber(accountNo);
        if (accEntity.isPresent()) {
            if (accEntity.get().getBalance() <= 0 || accEntity.get().getBalance() < amount) {
                throw new BusinessException("Insufficient account Balance");
            }
            AccountEntity accountEntity = accEntity.get();
            Double balance = accountEntity.getBalance();
            Double newBalance = balance - amount;
            accountEntity.setBalance(newBalance);
            accountEntity = accountRepository.save(accountEntity);
            BeanUtils.copyProperties(accountEntity, accountDTO);
            accountDTO.setMessage("Debited : " + amount + " Rupees from your account,Now your balance is:  " + accountEntity.getBalance() + " Rupees");
            accountDTO.setAmount(null);
            addTransaction(amount, accountEntity,"debit");
        } else {
            ErrorModel model = new ErrorModel();
            model.setMessage("Sorry no account found");
            model.setCode("ACCOUNT_001");
            List<ErrorModel> errors = new ArrayList<>();
            errors.add(model);
            throw new BusinessException(errors);
        }
        return accountDTO;
    }


    /***
     *
     * @param accountNo
     * @return
     * Get all the transactions done by an account holder
     */
    @Override
    public List<TransactionDTO> getAllTransactions(Long accountNo) {
        Optional<List<TransactionEntity>> transactionEntityList = transactionRepository.findByAccountNumber(accountNo);
        List<TransactionDTO> transactionList = new ArrayList<>();
        if (transactionEntityList.isPresent()) {
            List<TransactionEntity> transactionsList = transactionEntityList.get();
            for (TransactionEntity entity : transactionsList) {
                TransactionDTO transDTO = new TransactionDTO();
                transDTO.setTransactionId(entity.getTransactionId());
                transDTO.setTransactionType(entity.getTransactionType());
                transDTO.setAmount(entity.getAmount());
                transDTO.setTime(entity.getTime());
                transactionList.add(transDTO);
            }
        } else {
            ErrorModel model = new ErrorModel();
            model.setMessage("Sorry no transactions found");
            model.setCode("TRANSACT_001");
            List<ErrorModel> errors = new ArrayList<>();
            errors.add(model);
            throw new BusinessException(errors);
        }
        return transactionList;
    }

    /***
     *
     * @param customerId
     * @return
     * All the details of an account holder if his account exists
     */
    @Override
    public AccountCreatedDTO myaccount(Long customerId) {
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);
        AccountCreatedDTO accountCreatedDTO = new AccountCreatedDTO();
        if (customer.isPresent()) {
            CustomerEntity cust = customer.get();
            accountCreatedDTO.setAccountNo(cust.getAccount().getAccountNumber());
            accountCreatedDTO.setUserName(cust.getOwnerName());
            accountCreatedDTO.setIFSCcode("HDFC_112");
            accountCreatedDTO.setAdhaar(cust.getAdhaar());
            accountCreatedDTO.setOwnerEmail(cust.getOwnerEmail());
            accountCreatedDTO.setBalance(cust.getAccount().getBalance());
            accountCreatedDTO.setCustomerId(cust.getId());
            accountCreatedDTO.setPhone(cust.getPhone());
        }
        else {
            throw new BusinessException("No customer account found");
        }
        return accountCreatedDTO;
    }

    private void addTransaction(Double amount, AccountEntity accountEntity,String type) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAmount(amount);
        transactionEntity.setTransactionType(type);
        transactionEntity.setTime(LocalDateTime.now());
        transactionEntity.setAccountNumber(accountEntity.getAccountNumber());
        transactionEntity = transactionRepository.save(transactionEntity);
    }

}
