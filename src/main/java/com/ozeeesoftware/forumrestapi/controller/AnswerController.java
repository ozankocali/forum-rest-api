package com.ozeeesoftware.forumrestapi.controller;

import com.ozeeesoftware.forumrestapi.model.answer.Answer;
import com.ozeeesoftware.forumrestapi.model.answer.AnswerDTO;
import com.ozeeesoftware.forumrestapi.service.answer.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @PostMapping("/create/{commentId}")
    public ResponseEntity<AnswerDTO> createAnswer(@PathVariable long commentId, @RequestBody Answer answer){
        return answerService.createAnswer(commentId,answer);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @GetMapping("/{answerId}")
    public ResponseEntity<AnswerDTO> getAnswerById(@PathVariable long answerId){
        return answerService.getAnswerById(answerId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @GetMapping("/commentId/{commentId}")
    public ResponseEntity<List<AnswerDTO>> getAllAnswersByCommentId(@PathVariable long commentId){
        return answerService.getAllAnswersByCommentId(commentId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @DeleteMapping("/delete/{answerId}")
    public ResponseEntity<AnswerDTO> deleteAnswerById(@PathVariable long answerId){
        return answerService.deleteAnswerById(answerId);
    }

}
