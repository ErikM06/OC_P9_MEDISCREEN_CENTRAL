package com.mediscreen.central.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (HttpStatus.BAD_REQUEST)
public class PatientAlreadyExistException extends RuntimeException{

    public PatientAlreadyExistException (String msg){
        super(msg);
    }
}
