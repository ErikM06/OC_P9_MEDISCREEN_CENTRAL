package com.mediscreen.central.customExceptions;

public class FamilyDoesNotMatchException extends Throwable {
    public FamilyDoesNotMatchException(String msg) {
        super(msg);
    }
}
