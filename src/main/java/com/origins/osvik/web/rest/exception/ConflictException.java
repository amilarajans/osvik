package com.origins.osvik.web.rest.exception;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public class ConflictException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ConflictException(String s) {
        super(s);
    }
}
