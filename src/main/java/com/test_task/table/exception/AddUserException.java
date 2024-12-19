package com.test_task.table.exception;

public class AddUserException extends RuntimeException{
    public AddUserException() {
    }

    public AddUserException(String message) {
        super(message);
    }
}
