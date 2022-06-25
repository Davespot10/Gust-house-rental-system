package com.example.mppproject.exceptionResponse.propertyException;

public class PropertyNotFoundException extends RuntimeException{
    public PropertyNotFoundException(String message) {
        super(message);
    }
}
