package io.job.my_app.exception;

public class ResourceNotFoundException extends Throwable {
    String resourceName;
    String fieldName;
    Integer fieldValue;

    String fieldValue1;
    public ResourceNotFoundException(String resourceName, String fieldName, Integer fieldValue) {
        super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue1) {
        super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue1));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue1 = fieldValue1;
    }

    public ResourceNotFoundException(String rentPersonNotFound) {
        super(rentPersonNotFound);
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Integer fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getFieldValue1() {
        return fieldValue1;
    }

    public void setFieldValue1(String fieldValue1) {
        this.fieldValue1 = fieldValue1;
    }
}
