package com.example.mppproject.Model.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ExceptionResponse {

    private Date date;
    private HttpStatus httpStatus;
    private String message;

}
