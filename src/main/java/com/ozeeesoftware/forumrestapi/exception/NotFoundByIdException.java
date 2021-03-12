package com.ozeeesoftware.forumrestapi.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundByIdException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public NotFoundByIdException(String message){
        super(message);
    }

}
