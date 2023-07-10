package com.bikkadit.electronicstore.exception;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(){
        super("Resource not found !!");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}
