package com.origins.osvik.web.rest.exception;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public class NotPermittedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NotPermittedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotPermittedException(String message) {
        super(message);
    }

    public NotPermittedException(Throwable cause) {
        super(cause);
    }

    public NotPermittedException() {
    }
}
