package com.origins.osvik.web.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
@ControllerAdvice
public class ExceptionHandlerAdvice
{
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    @ResponseBody
    public ErrorInfo handleNotFound(Exception e)
    {
        return new ErrorInfo(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    @ResponseBody
    public ErrorInfo handleBadRequest(Exception e)
    {
        return new ErrorInfo(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({InternalServerErrorException.class})
    @ResponseBody
    public ErrorInfo handleInternalServerError(Exception e)
    {
        return new ErrorInfo(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({NotPermittedException.class})
    @ResponseBody
    public ErrorInfo handleNotPermittedException(Exception e)
    {
        return new ErrorInfo(e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ConflictException.class})
    @ResponseBody
    public ErrorInfo handleConflict(Exception e)
    {
        return new ErrorInfo(e.getMessage());
    }
}
