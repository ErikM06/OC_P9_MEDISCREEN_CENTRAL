package com.mediscreen.central.customExceptions;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 400) {
            return new PatientAlreadyExistException("This Patient already exist!");
        }
        if (response.status() == 404){
            return new NotFoundException("Not found !");
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
