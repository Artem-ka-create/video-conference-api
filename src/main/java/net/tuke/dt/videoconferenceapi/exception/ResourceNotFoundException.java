package net.tuke.dt.videoconferenceapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{



    private String resourceName;
    private String fieldName;
    private long fielValue;

    private String strValue;

    public ResourceNotFoundException(String resourceName, String fieldName, long fielValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fielValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fielValue = fielValue;
    }
    public ResourceNotFoundException(String resourceName, String fieldName, String fielValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fielValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.strValue = fielValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public long getFielValue() {
        return fielValue;
    }

    public String getStrValue() {
        return strValue;
    }
}
