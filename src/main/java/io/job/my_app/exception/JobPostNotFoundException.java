package io.job.my_app.exception;

public class JobPostNotFoundException extends RuntimeException{

    public JobPostNotFoundException(String message) {
        super(message);
    }

}
