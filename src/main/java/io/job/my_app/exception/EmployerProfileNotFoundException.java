package io.job.my_app.exception;

public class EmployerProfileNotFoundException extends RuntimeException {

    public EmployerProfileNotFoundException(String message) {
        super(message);
    }
}