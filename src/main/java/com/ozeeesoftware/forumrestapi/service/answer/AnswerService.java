package com.ozeeesoftware.forumrestapi.service.answer;

import com.ozeeesoftware.forumrestapi.model.answer.Answer;
import org.springframework.http.ResponseEntity;

public interface AnswerService {

    ResponseEntity createAnswer(long commentId, Answer answer);
    ResponseEntity getAnswerById(long answerId);
    ResponseEntity getAllAnswersByCommentId(long commentId);
    ResponseEntity deleteAnswerById(long answerId);

}
