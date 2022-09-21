package com.example.ad.exception;

public class BadRequestException extends Exception {

    public BadRequestException(){
        super("Invalid Request");
    }
}
