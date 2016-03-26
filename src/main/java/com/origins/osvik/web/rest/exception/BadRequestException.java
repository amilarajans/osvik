package com.origins.osvik.web.rest.exception;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequestException(String s) {
        super(s);
    }
}
