package com.rbi.HDFC.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Getter
@Setter
public class AccountDTO {

    private Long accountId;
    private Double balance;
}
