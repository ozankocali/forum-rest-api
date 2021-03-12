package com.ozeeesoftware.forumrestapi.service;

import com.ozeeesoftware.forumrestapi.model.user.User;
import com.ozeeesoftware.forumrestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService{


    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity createUser(User user){
        User createdUser=userRepository.save(user);
        if(createdUser!=null){
            return ResponseEntity.ok(createdUser);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers=userRepository.findAll();
        List<User> allUserRoles=new ArrayList<>();

        for (User user:allUsers){
            if(!user.getRole().equals("ADMIN")){
                allUserRoles.add(user);
            }
        }
        return ResponseEntity.ok(allUserRoles);
    }

}
