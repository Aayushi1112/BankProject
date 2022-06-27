package com.rbi.HDFC.service;

import com.rbi.HDFC.dto.BeneficiaryDTO;
import com.rbi.HDFC.dto.BeneficiaryListDTO;
import com.rbi.HDFC.dto.CustomerRegisterDTO;
import com.rbi.HDFC.dto.TransferDTO;

import java.util.List;

public interface BeneficiaryService {
    BeneficiaryListDTO addBeneficiary(BeneficiaryListDTO beneficiaryListDTO);

    String transferMoney(TransferDTO transferDTO);

    BeneficiaryListDTO getAllBeneficiaries(Long customerId);

    String deleteBeneficiary(TransferDTO transferDTO);
}
