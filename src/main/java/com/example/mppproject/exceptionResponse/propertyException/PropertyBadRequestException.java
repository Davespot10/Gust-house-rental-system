package com.example.mppproject.exceptionResponse.propertyException;

public class PropertyBadRequestException extends RuntimeException{
    public PropertyBadRequestException(String message) {
        super(message);
    }
}
