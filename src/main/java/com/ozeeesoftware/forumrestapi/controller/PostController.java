package com.ozeeesoftware.forumrestapi.controller;

import com.ozeeesoftware.forumrestapi.model.post.Post;
import com.ozeeesoftware.forumrestapi.service.post.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody Post post){
        return postService.createPost(post);
    }

    @PutMapping("/update")
    public ResponseEntity<Post> updatePost(@RequestBody Post post)throws InvocationTargetException, IllegalAccessException{
        return postService.updatePost(post);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable long postId){
        return postService.getPostById(postId);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Post> deletePostById(@PathVariable long postId){
        return postService.deletePostById(postId);
    }

    @GetMapping("/approve/{postId}")
    public ResponseEntity<Post> approvePost(@PathVariable long postId){
        return postService.approvePost(postId);
    }

    @GetMapping("/approve")
    public ResponseEntity approveAll(){
        return postService.approveAll();
    }

    @GetMapping("/reject/{postId}")
    public ResponseEntity<Post> rejectPost(@PathVariable long postId){
        return postService.rejectPost(postId);
    }

    @GetMapping("/reject")
    public ResponseEntity rejectAll(){
        return postService.rejectAll();
    }

    @GetMapping("/approved")
    public ResponseEntity<List<Post>> approvedPosts(){
        return postService.approvedPosts();
    }

}
