package io.job.my_app.Entity.Security;

public enum Status {

    ACTIVE("Active"),
    DEACTIVATE("Deactivate");


    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static Status fromString(String status) {
        for (Status s : Status.values()) {
            if (s.getStatus().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + status);
    }
}
