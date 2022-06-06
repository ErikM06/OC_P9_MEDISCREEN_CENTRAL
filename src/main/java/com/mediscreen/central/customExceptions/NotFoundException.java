package com.mediscreen.central.customExceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException (String msg){
        super(msg);
    }
}
