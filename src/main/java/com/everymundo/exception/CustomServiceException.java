package com.everymundo.exception;


/**
 * {@link CustomServiceException} class
 * Personalized exception for customer-service MicroServices.
 *
 * @author Angel Lecuona
 *
 */
public class CustomServiceException extends Exception {

    private static final long serialVersionUID = -598523535008158605L;

    /**
     * Constructor with message and cause params
     * @param message
     * @param cause
     */
    public CustomServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
