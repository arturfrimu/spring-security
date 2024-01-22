package com.gourav.restapi.exception;

public class PetNotFountException extends RuntimeException {
    public PetNotFountException(String message) {
        super(message);
    }
}
