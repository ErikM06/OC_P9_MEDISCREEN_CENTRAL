package com.mediscreen.central.customExceptions;

public class PatientAlreadyExistException extends RuntimeException{

    public PatientAlreadyExistException (String msg){
        super(msg);
    }
}
