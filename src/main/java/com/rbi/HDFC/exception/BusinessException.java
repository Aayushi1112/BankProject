package com.rbi.HDFC.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    private List<ErrorModel> errors;
    private String message;;

    public BusinessException(List<ErrorModel> errors){

        this.errors = errors;
    }
    public BusinessException(String message){

        this.message=message;
    }

}
