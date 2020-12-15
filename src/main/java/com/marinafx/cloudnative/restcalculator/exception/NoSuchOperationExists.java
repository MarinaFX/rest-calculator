package com.marinafx.cloudnative.restcalculator.exception;

public class NoSuchOperationExists extends RuntimeException{

    public NoSuchOperationExists(String message) {
        super(message);
    }
}
