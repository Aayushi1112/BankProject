
package com.rbi.HDFC.controller;

import com.rbi.HDFC.dto.*;
import com.rbi.HDFC.service.BeneficiaryService;
import com.rbi.HDFC.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 *
 * 1.Add a Beneficiary to the user account
 * 2.Get details of all the beneficiaries added to the account
 * 3.Transfer Money to a Beneficiary
 * 4.Delete a beneficiary
 */

@RestController
@RequestMapping("/api/v1/beneficiary")
public class BeneficiaryController {
    @Autowired
    private BeneficiaryService beneficiaryService;

    @PostMapping("/addBeneficiary")
    public ResponseEntity<BeneficiaryListDTO> addBeneficiary(@RequestBody BeneficiaryListDTO beneficiaryListDTO) {
        BeneficiaryListDTO beneficiaryList = beneficiaryService.addBeneficiary(beneficiaryListDTO);
        ResponseEntity<BeneficiaryListDTO> responseEntity = new ResponseEntity<>(beneficiaryList, HttpStatus.OK);
        return responseEntity;
    }
    @PostMapping("/allBeneficiaries")
    public ResponseEntity<BeneficiaryListDTO> getAllBeneficiaries(@RequestBody AccountCreatedDTO accountCreatedDTO) {
        BeneficiaryListDTO beneficiaryList = beneficiaryService.getAllBeneficiaries(accountCreatedDTO.getCustomerId());
        ResponseEntity<BeneficiaryListDTO> responseEntity = new ResponseEntity<>(beneficiaryList, HttpStatus.OK);
        return responseEntity;
    }
    @PostMapping("/transfer_money")
    public ResponseEntity<String> transferMoney(@RequestBody TransferDTO transferDTO) {
        String msg = beneficiaryService.transferMoney(transferDTO);
        ResponseEntity<String> responseEntity = new ResponseEntity<>(msg, HttpStatus.OK);
        return responseEntity;
    }
    @PostMapping("/deleteBeneficiary")
    public ResponseEntity<String> deleteBeneficiary(@RequestBody TransferDTO transferDTO) {
        String msg = beneficiaryService.deleteBeneficiary(transferDTO);
        ResponseEntity<String> responseEntity = new ResponseEntity<>(msg, HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("/deletebeneficiary/{custId}/{benId}")
    public ResponseEntity<String> deleteProperty(@PathVariable Long custId,@PathVariable Long benId){
        String msg = beneficiaryService.deleteBeneficiary(custId,benId);
        ResponseEntity<String> responseEntity = new ResponseEntity<>(msg, HttpStatus.OK);
        return responseEntity;
    }

}

