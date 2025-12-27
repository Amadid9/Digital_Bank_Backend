package com.example.AppGestionBank.exceptions;

public class CompteBancaireNotFoundException extends RuntimeException  {
    public CompteBancaireNotFoundException(String message) {
        super(message);
    }
}
