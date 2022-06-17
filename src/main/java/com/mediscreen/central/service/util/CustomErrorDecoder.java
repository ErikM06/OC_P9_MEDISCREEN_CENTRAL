package com.mediscreen.central.service.util;

import com.mediscreen.central.customExceptions.NotFoundException;
import com.mediscreen.central.customExceptions.PatientAlreadyExistException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String invoker, Response response) {

        if(response.status() == 400 ) {
            return new PatientAlreadyExistException("patient already exist");
        }
        if(response.status()==404) {
            return new NotFoundException( "patient not found");
        }
        return defaultErrorDecoder.decode(invoker, response);
    }
}
