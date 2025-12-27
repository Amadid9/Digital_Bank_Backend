package com.example.AppGestionBank.exceptions;

public class ClientNotFoundException extends RuntimeException  {

    public ClientNotFoundException(String message) {
        super(message);
    }
}
