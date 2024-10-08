package io.job.my_app.payloads;

public class JwtAuthResponse {
    private String token;
    private String message;
    public JwtAuthResponse() {
        super();

    }

    public JwtAuthResponse(String token2,String message) {
        super();
        this.token = token2;
        this.message=message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
