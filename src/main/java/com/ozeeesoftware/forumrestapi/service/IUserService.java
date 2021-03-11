package com.ozeeesoftware.forumrestapi.service;

import com.ozeeesoftware.forumrestapi.model.user.User;
import org.springframework.http.ResponseEntity;

public interface IUserService {

    ResponseEntity createUser(User user);
    ResponseEntity getAllUsers();
    ResponseEntity getUserById(long userId);
    ResponseEntity deleteUserById(long userId);

}
