package com.rbi.HDFC.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferDTO {
    private Long custId;
    private Long beneficiaryId;
    private Double amount;
}
