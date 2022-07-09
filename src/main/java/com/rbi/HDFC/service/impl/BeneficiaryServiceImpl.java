package com.rbi.HDFC.service.impl;

import com.rbi.HDFC.dto.BeneficiaryDTO;
import com.rbi.HDFC.dto.BeneficiaryListDTO;
import com.rbi.HDFC.dto.TransferDTO;
import com.rbi.HDFC.entity.BeneficiaryEntity;
import com.rbi.HDFC.entity.CustomerEntity;
import com.rbi.HDFC.exception.BusinessException;
import com.rbi.HDFC.exception.ErrorModel;
import com.rbi.HDFC.repository.BeneficiaryRepository;
import com.rbi.HDFC.repository.CustomerRepository;
import com.rbi.HDFC.repository.TransactionRepository;
import com.rbi.HDFC.service.BeneficiaryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class BeneficiaryServiceImpl implements BeneficiaryService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    BeneficiaryRepository beneficiaryRepository;

    /***
     *
     * @param beneficiaryListDTO
     * @return
     * Add a number of beneficiaries to a user account by using a one to many mapping
     * An account  can have a number of beneficiaries associated with it
     * Functionality:
     * First go to the customer table and find the account holder and all his beneficiaries
     */
    @Override
    public BeneficiaryListDTO addBeneficiary(BeneficiaryListDTO beneficiaryListDTO) {
        Optional<CustomerEntity> custEntity = customerRepository.findById(beneficiaryListDTO.getCustomerId());
        CustomerEntity custEntityget = custEntity.get();
        List<BeneficiaryEntity> beneficiaryListnew = new ArrayList<>();
        List<BeneficiaryDTO> beneficiaryList = new ArrayList<>();
        BeneficiaryListDTO beneficiaryListreturn = new BeneficiaryListDTO();
        if (custEntity.isPresent()) {
            //getting existing beneficiaries from the database
            List<BeneficiaryEntity> existBenefitry = custEntity.get().getBeneficiaries();
            System.out.println("No of beneficiaries atached with your account are"+existBenefitry.size());
            List<BeneficiaryDTO> beneficiaryDTOList = beneficiaryListDTO.getBeneficiaries();
//now take beneficieries from the dto and save it to the beneficiary table
            for (BeneficiaryDTO bdto : beneficiaryDTOList) {
                BeneficiaryEntity be = new BeneficiaryEntity();
                BeanUtils.copyProperties(bdto, be);
                be = beneficiaryRepository.save(be);
                BeanUtils.copyProperties(be, bdto);
                beneficiaryListnew.add(be);
                beneficiaryList.add(bdto);
            }
            //add all beneficiaries to the list to be returned to the customer
            beneficiaryListreturn.setBeneficiaries(beneficiaryList);
            existBenefitry.addAll(beneficiaryListnew);
            System.out.println("your existing beneficiary list now updated and new size is"+existBenefitry.size());
            custEntityget.setBeneficiaries(existBenefitry);
            custEntityget = customerRepository.save(custEntityget);
        } else {
            throw new BusinessException("No account found for : " + beneficiaryListDTO.getCustomerId());
        }
        return beneficiaryListreturn;
    }

    /***
     *
     * @param transferDTO
     * @return
     * transfer money to a beneficiary
     * find the customer by id and check his balance if amount to be transferred is greater then the account balance
     * then tell the customer that you do not have sufficient balance
     */
    @Override
    public String transferMoney(TransferDTO transferDTO) {
        Optional<CustomerEntity> custEntity = customerRepository.findById(transferDTO.getCustId());
        if (custEntity.isPresent()) {
            CustomerEntity cust = custEntity.get();
            cust.getAccount().getBalance();
            if (transferDTO.getAmount() > cust.getAccount().getBalance()) {
                throw new BusinessException("Sorry you do not have sufficient balance in your account to make a transfer");
            } else {
                List<BeneficiaryEntity> be = cust.getBeneficiaries();
                for (BeneficiaryEntity beneficiaryEntity : be) {
                    if (transferDTO.getBenId().equals(beneficiaryEntity.getId())) {
                        System.out.println("Beneficiary is found");
                        Double newBalance = cust.getAccount().getBalance() - transferDTO.getAmount();
                        cust.getAccount().setBalance(newBalance);
                        beneficiaryEntity.setMoneyTransferred(beneficiaryEntity.getMoneyTransferred() + transferDTO.getAmount());
                        beneficiaryEntity=beneficiaryRepository.save(beneficiaryEntity);
                        cust = customerRepository.save(cust);
                        break;
                    }
                }
            }
        } else {
            throw new BusinessException("Sorry no customer account found for " + transferDTO.getCustId());
        }
        return "Sucessfully transferred your money";
    }

    /***
     *
     * @param customerId
     * @return
     * Get all the beneficiers associated with an account
     */

    @Override
    public BeneficiaryListDTO getAllBeneficiaries(Long customerId) {
        Optional<CustomerEntity> custEntity = customerRepository.findById(customerId);
        List<BeneficiaryDTO> beneficiaryList = new ArrayList<>();
        BeneficiaryListDTO beneficiaryListreturn = new BeneficiaryListDTO();
        if (custEntity.isPresent()) {
            List<BeneficiaryEntity> existBenefitry = custEntity.get().getBeneficiaries();
            for (BeneficiaryEntity beneficiaryEntity : existBenefitry) {
                BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();
                BeanUtils.copyProperties(beneficiaryEntity, beneficiaryDTO);
                beneficiaryList.add(beneficiaryDTO);
            }
            beneficiaryListreturn.setBeneficiaries(beneficiaryList);
            beneficiaryListreturn.setCustomerId(customerId);
        } else {
            throw new BusinessException("No customer found");
        }
        return beneficiaryListreturn;
    }

    /***
     *
     * @param transferDTO
     * @return
     * Delete a beneficiary whose id is specified by the customer
     */
    @Override
    public String deleteBeneficiary(TransferDTO transferDTO) {
        Optional<CustomerEntity> custEntity = customerRepository.findById(transferDTO.getCustId());
        boolean flag = false;
        if (custEntity.isPresent()) {
            CustomerEntity cust = custEntity.get();
            List<BeneficiaryEntity> be = cust.getBeneficiaries();
            for (BeneficiaryEntity beneficiaryEntity : be) {
                if (transferDTO.getBenId().equals(beneficiaryEntity.getId())) {
                    System.out.println("Beneficiary is found");
                    //beneficiaryRepository.deleteByBenId(transferDTO.getBenId());

                    Long id=transferDTO.getBenId();
                    beneficiaryRepository.deleteById(id);
                    flag = true;
                }
            }
            if (flag == false) {
                throw new BusinessException("No Beneficiary found for this id:" + transferDTO.getBenId());
            }
        } else {
            throw new BusinessException("No customer found for : " + transferDTO.getCustId());
        }
        return "deleted";
    }
}
