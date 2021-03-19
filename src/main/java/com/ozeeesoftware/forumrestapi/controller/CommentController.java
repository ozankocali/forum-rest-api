package com.ozeeesoftware.forumrestapi.controller;

import com.ozeeesoftware.forumrestapi.model.comment.Comment;
import com.ozeeesoftware.forumrestapi.model.comment.CommentDTO;
import com.ozeeesoftware.forumrestapi.service.comment.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @PostMapping("/create/{postId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable long postId,@RequestBody Comment comment){
        return commentService.createComment(postId,comment);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable long commentId){
        return commentService.getCommentById(commentId);
    }

    @GetMapping("/postId/{postId}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByPostId(@PathVariable long postId){
        return commentService.getAllCommentsByPostId(postId);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<CommentDTO> deleteCommentById(@PathVariable long commentId){
        return commentService.deleteCommentById(commentId);
    }

}
