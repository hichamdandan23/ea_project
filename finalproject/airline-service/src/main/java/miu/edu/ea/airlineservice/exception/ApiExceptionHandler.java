package miu.edu.ea.airlineservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value=ApiCustomException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiCustomException ex){
        // create payload
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        // return response
        return new ResponseEntity<>(exceptionResponse, badRequest);
    }

    @ExceptionHandler(value=Exception.class)
    public ResponseEntity<Object> handleException(Exception ex){
        // create payload
        HttpStatus badRequest = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        // return response
        return new ResponseEntity<>(exceptionResponse, badRequest);
    }
}
