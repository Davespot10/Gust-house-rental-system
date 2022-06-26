package com.example.mppproject.exceptionResponse;

import com.example.mppproject.Model.exception.ExceptionResponse;
import com.example.mppproject.exceptionResponse.propertyException.PropertyBadRequestException;
import com.example.mppproject.exceptionResponse.propertyException.PropertyNotFoundException;
import com.example.mppproject.exceptionResponse.reservationException.ReservationNotFoundException;
import com.example.mppproject.exceptionResponse.userException.UserBadRequestException;
import com.example.mppproject.exceptionResponse.userException.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Controller
@ControllerAdvice
public class ResponseEntityException extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ExceptionResponse> allException(Exception exception, WebRequest webRequest){
//        ExceptionResponse message = new ExceptionResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR,exception.getMessage());
//        return new ResponseEntity(message,HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(PropertyNotFoundException.class)
    public ResponseEntity<ExceptionResponse> propertyNotFound
            (Exception exception, WebRequest webRequest){
        ExceptionResponse message = new ExceptionResponse(
                new Date(), HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity(message,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ReservationNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<ExceptionResponse> reservationNotFound
            (Exception exception, WebRequest webRequest){
        ExceptionResponse message = new ExceptionResponse
                (new Date(), HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity(message,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({PropertyBadRequestException.class, UserBadRequestException.class})
    public ResponseEntity<ExceptionResponse> propertyBadRequest
            (Exception exception, WebRequest webRequest){
        ExceptionResponse message = new ExceptionResponse
                (new Date(), HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
    }

}
