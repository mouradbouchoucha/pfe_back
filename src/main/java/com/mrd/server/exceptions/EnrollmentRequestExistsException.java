package com.mrd.server.exceptions;

public class EnrollmentRequestExistsException extends RuntimeException {
    public EnrollmentRequestExistsException(String message) {
        super(message);
    }
}