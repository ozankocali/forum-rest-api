package com.ozeeesoftware.forumrestapi.controller;

import com.ozeeesoftware.forumrestapi.model.comment.Comment;
import com.ozeeesoftware.forumrestapi.model.comment.CommentDTO;
import com.ozeeesoftware.forumrestapi.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @PostMapping("/create/{postId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable long postId,@RequestBody Comment comment){
        return commentService.createComment(postId,comment);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable long commentId){
        return commentService.getCommentById(commentId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @GetMapping("/postId/{postId}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByPostId(@PathVariable long postId){
        return commentService.getAllCommentsByPostId(postId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<CommentDTO> deleteCommentById(@PathVariable long commentId){
        return commentService.deleteCommentById(commentId);
    }

}
