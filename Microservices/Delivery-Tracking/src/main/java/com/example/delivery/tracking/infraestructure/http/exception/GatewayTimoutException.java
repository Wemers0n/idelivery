package com.example.delivery.tracking.infraestructure.http.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
public class GatewayTimoutException extends RuntimeException{

    public GatewayTimoutException(){

    }

    public GatewayTimoutException(Throwable cause){
        super(cause);
    }
}
