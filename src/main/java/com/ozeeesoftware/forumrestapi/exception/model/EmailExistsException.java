package com.ozeeesoftware.forumrestapi.exception.model;

public class EmailExistsException extends Exception{

    public EmailExistsException(String message) {
        super(message);
    }
}
