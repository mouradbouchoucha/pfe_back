package com.mrd.server.exceptions;

public class TraineeAlreadyEnrolledException extends RuntimeException {
    public TraineeAlreadyEnrolledException(String message) {
        super(message);
    }
}
