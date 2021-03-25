package com.ozeeesoftware.forumrestapi.controller;

import com.ozeeesoftware.forumrestapi.model.post.Post;
import com.ozeeesoftware.forumrestapi.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody Post post){
        return postService.createPost(post);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @PutMapping("/update")
    public ResponseEntity<Post> updatePost(@RequestBody Post post)throws InvocationTargetException, IllegalAccessException{
        return postService.updatePost(post);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR'")
    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts(){
        return postService.getAllPosts();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable long postId){
        return postService.getPostById(postId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Post> deletePostById(@PathVariable long postId){
        return postService.deletePostById(postId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping("/approve/{postId}")
    public ResponseEntity<Post> approvePost(@PathVariable long postId){
        return postService.approvePost(postId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping("/approve")
    public ResponseEntity approveAll(){
        return postService.approveAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping("/reject/{postId}")
    public ResponseEntity<Post> rejectPost(@PathVariable long postId){
        return postService.rejectPost(postId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping("/reject")
    public ResponseEntity rejectAll(){
        return postService.rejectAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @GetMapping("/approved")
    public ResponseEntity<List<Post>> approvedPosts(){
        return postService.approvedPosts();
    }

}
