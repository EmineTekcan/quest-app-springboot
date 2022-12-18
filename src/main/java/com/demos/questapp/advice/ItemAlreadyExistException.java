package com.demos.questapp.advice;

public class ItemAlreadyExistException extends RuntimeException{
    public ItemAlreadyExistException(String message){
        super(message);
    }
}
