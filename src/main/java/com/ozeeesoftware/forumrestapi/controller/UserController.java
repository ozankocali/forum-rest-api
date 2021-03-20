package com.ozeeesoftware.forumrestapi.controller;

import com.ozeeesoftware.forumrestapi.config.jwt.JwtUtil;
import com.ozeeesoftware.forumrestapi.model.AuthRequest;
import com.ozeeesoftware.forumrestapi.model.CreateUser;
import com.ozeeesoftware.forumrestapi.model.user.User;
import com.ozeeesoftware.forumrestapi.service.user.UserDetailsService;
import com.ozeeesoftware.forumrestapi.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    @Autowired
    private UserService service;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;


    String getAuthUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        return service.create(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public ResponseEntity update(@RequestBody User user) {
        return service.update(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> list() {
        return service.list();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return service.deleteById(id);
    }
//-----

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/self")
    public ResponseEntity<User> getSelf() {
        return service.getByUsername(getAuthUserName());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/updateProfile")
    public ResponseEntity updateProfile(@RequestBody User user) {
        return service.updateProfile(getAuthUserName(), user);
    }
//-----

    @PostMapping("/login")
    public String creteToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
            return jwtUtil.generateToken(userDetails);
        } catch (AuthenticationException ex) {
            throw new Exception("Incorrect username or password", ex);
        }
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody CreateUser user) {
        return service.register(user);
    }

    @GetMapping("/count")
    public ResponseEntity getUserCount(){
        return service.getUserCount();
    }

}
