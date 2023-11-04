package net.tuke.dt.videoconferenceapi.exception;

import org.springframework.http.HttpStatus;

public class MeetAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public MeetAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public MeetAPIException(String message, HttpStatus status, String message1) {
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