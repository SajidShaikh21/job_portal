package io.job.my_app.exception;

public class UserSubDomainsNotFoundException extends RuntimeException {

    public UserSubDomainsNotFoundException(String message) {
        super(message);
    }

    public UserSubDomainsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

