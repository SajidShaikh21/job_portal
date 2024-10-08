package io.job.my_app.payloads;

public class ApiResponse {
    private String message;
    private boolean success;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public ApiResponse() {
        super();

    }
    public ApiResponse(String message, boolean success) {
        super();
        this.message = message;
        this.success = success;
    }
}
