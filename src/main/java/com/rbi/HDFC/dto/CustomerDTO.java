package com.rbi.HDFC.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDTO {
    private Long id;
    @NotNull(message = "Owner Email is mandatory")
    @NotEmpty(message = "Owner Email cannot be empty")
    @Size(min = 1, max = 50, message = "Owner Email should be between 1 to 50 characters in length")
    private String ownerEmail;
    @NotNull(message = "Name is a required field")
    private String ownerName;
    @NotNull(message = "Password is mandatory")
    private String password;
    @NotNull(message = "Phone Number is mandatory")
    @Size(min = 9, message = "Not a valid phone number")
    private String phone;
    @NotNull(message = "Adhaar registration is mandatory")
    @Size(min = 8, message = "Not a valid adhaar number")
    private String adhaar;
    private Long accountNo;
    private String beneficiaries;
}
