package io.job.my_app.exception;


import io.job.my_app.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.ServiceNotFoundException;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(UserDisabledException.class)
    public ResponseEntity<ApiResponse> userDisabledExceptionHandler(UserDisabledException ex){

        String message=ex.getMessage();
        ApiResponse apiResponse =new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){

        String message=ex.getMessage();
        ApiResponse apiResponse =new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse> invalidCredentialsExceptionHandler(InvalidCredentialsException ex){

        String message=ex.getMessage();
        ApiResponse apiResponse =new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ApiResponse> userAlreadyExistExceptionHandler(UserAlreadyExistException ex){

        String message=ex.getMessage();
        ApiResponse apiResponse =new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<ApiResponse> serviceNotFoundExceptionHandler(ServiceNotFoundException ex){

        String message=ex.getMessage();
        ApiResponse apiResponse =new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(BrokerProfileNotFoundException.class)
    public ResponseEntity<ApiResponse> brokerProfileNotFoundExceptionHandler(BrokerProfileNotFoundException ex){

        String message=ex.getMessage();
        ApiResponse apiResponse =new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<ApiResponse> invalidOtpExceptionHandler(InvalidOtpException ex){

        String message=ex.getMessage();
        ApiResponse apiResponse =new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(OtpExpiredException.class)
    public ResponseEntity<ApiResponse> otpExpiredExceptionHandler(OtpExpiredException ex){

        String message=ex.getMessage();
        ApiResponse apiResponse =new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PageNotFoundException.class)
    public ResponseEntity<ApiResponse> pageNotFoundException(PageNotFoundException ex){

        String message=ex.getMessage();
        ApiResponse apiResponse =new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
    }
}
