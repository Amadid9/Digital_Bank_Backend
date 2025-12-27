package com.example.AppGestionBank.exceptions;

public class CompteAlreadyExistsException extends RuntimeException {
    public CompteAlreadyExistsException(String message) {
        super(message);
    }
}
