package com.ozeeesoftware.forumrestapi.service.user;

import com.ozeeesoftware.forumrestapi.model.BaseModel;
import com.ozeeesoftware.forumrestapi.model.CreateUser;
import com.ozeeesoftware.forumrestapi.model.other.Message;
import com.ozeeesoftware.forumrestapi.model.user.Role;
import com.ozeeesoftware.forumrestapi.model.user.User;
import com.ozeeesoftware.forumrestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public ResponseEntity<User> create(User entity) {
        entity.setUserName(entity.getUserName());
        User createdEntity = repo.save(entity);
        if (createdEntity != null) {
            return new ResponseEntity<>(createdEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<User> update(User user) {
        User existing = repo.findById(user.getId()).orElse(null);
        if (existing == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (user.isResetPassword()) {
            existing.setPassword(user.getEmail());
            existing.setResetPassword(false);
        }

        existing.setUserName(user.getUserName());
        existing.setEmail(user.getEmail());
        existing.setName(user.getName());
        existing.setSurname(user.getSurname());
        existing.setRole(user.getRole());

        return new ResponseEntity<>(repo.save(existing), HttpStatus.OK);
    }

    public ResponseEntity<List<User>> list() {
        List<User> users = repo.findAll();
        List<User> entityList = new ArrayList<>();
        for (User user : users) {
            if (user.getUserName() != null) {
                if (!user.getUserName().equals("admin")) {
                    entityList.add(user);
                }
            }
        }
        return new ResponseEntity<>(entityList, HttpStatus.OK);
    }

    public ResponseEntity<User> getById(long id) {
        User entity = repo.findById(id).orElse(null);
        User authUser = repo.findByUsername(getAuthUserName());

        if (entity != null) {
            if (authUser.getRole().equals(Role.ADMIN) || authUser.getId() == entity.getId()) {
                return new ResponseEntity<>(entity, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<User> deleteById(long id) {
        User entity = repo.findById(id).orElse(null);
        User user = repo.findByUsername(getAuthUserName());
        if (user.getId() == id) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return deleteById(entity, repo);
    }

    public ResponseEntity<User> getByUsername(String username) {
        User user = repo.findByUsername(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<User> updateProfile(String authUserName, User user) {
        User originalUser = repo.findByUsername(authUserName);
        originalUser.setName(user.getName());
        originalUser.setSurname(user.getSurname());
        originalUser.setEmail(user.getEmail());
        originalUser.setUserName(user.getEmail());


        repo.save(originalUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity register(CreateUser user) {
        User entity = new User();

        User existingUsername = repo.findByUsername(user.getUsername());
        if (existingUsername != null) {
            return new ResponseEntity<>(Message.USERNAME_ALREADY_EXISTS, HttpStatus.CONFLICT);
        }

        User existingEmail = repo.findByEmail(user.getEmail());
        if (existingEmail != null) {
            return new ResponseEntity<>(Message.EMAIL_ALREADY_EXISTS, HttpStatus.CONFLICT);
        }

        // assign user role
        entity.setRole(Role.USER);
        entity.setEmail(user.getEmail());
        entity.setUserName(user.getUsername());
        entity.setName(user.getName());
        entity.setSurname(user.getSurname());
        entity.setPassword(user.getPassword());

        User savedUser = repo.save(entity);
        if (savedUser != null) {
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Message.SYSTEM_ERROR, HttpStatus.CONFLICT);
        }
    }

    protected String getAuthUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    ResponseEntity deleteById(BaseModel entity, JpaRepository repo) {
        if (entity != null) {
            entity.setDeleted(true);
            repo.save(entity);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity getUserCount() {
        return new ResponseEntity<>(repo.count(), HttpStatus.OK);
    }


}
