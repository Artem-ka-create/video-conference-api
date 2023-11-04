package net.tuke.dt.videoconferenceapi.exception;

import org.springframework.http.HttpStatus;

public class EntityAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public EntityAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public EntityAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
