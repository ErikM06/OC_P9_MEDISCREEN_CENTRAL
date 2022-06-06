package com.mediscreen.central.service.util;

import com.mediscreen.central.customExceptions.NotFoundException;
import com.mediscreen.central.customExceptions.PatientAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientResponseException;

import java.io.IOException;

@Component
public class PatientClientErrorHandler implements ResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse httpResponse)
                throws IOException {

            return (
                    httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                            || httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
        }

        @Override
        public void handleError(ClientHttpResponse httpResponse)
                throws IOException {

            if (httpResponse.getStatusCode()
                    .series() == HttpStatus.Series.SERVER_ERROR) {
                throw  new RuntimeException();

            } else if (httpResponse.getStatusCode()
                    .series() == HttpStatus.Series.CLIENT_ERROR) {
                if (httpResponse.getStatusCode() == HttpStatus.BAD_REQUEST){
                throw new PatientAlreadyExistException("Patient already exist for name");
                }
                if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                    throw new NotFoundException("Patient not found");
                }
            }
        }

    public String mountMessage(RestClientResponseException error) {
        return error.getMessage();
    }



}
