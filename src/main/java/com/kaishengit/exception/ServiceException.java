package com.kaishengit.exception;

/**
 * Created by sunny on 2016/12/16.
 */
public class ServiceException extends RuntimeException {

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable th) {
        super(message, th);
    }

    public ServiceException(Throwable th) {
        super(th);
    }
}
