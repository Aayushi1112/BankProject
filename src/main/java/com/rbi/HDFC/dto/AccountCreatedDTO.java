package com.rbi.HDFC.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountCreatedDTO {
    private String userName;
    private String ownerEmail;
    private String phone;
    private String adhaar;

    private Long accountNo;
    private String IFSCcode;
    private Long customerId;
    private Double balance;
    private String password;






}
