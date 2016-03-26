package com.origins.osvik.web.rest.exception;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public class ErrorInfo {
    private String message;

    public ErrorInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
