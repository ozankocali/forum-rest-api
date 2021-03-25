package com.ozeeesoftware.forumrestapi.service.comment;

import com.ozeeesoftware.forumrestapi.model.comment.Comment;
import org.springframework.http.ResponseEntity;

public interface CommentService {

    ResponseEntity createComment(long postId, Comment comment);
    ResponseEntity getCommentById(long commentId);
    ResponseEntity getAllCommentsByPostId(long postId);
    ResponseEntity deleteCommentById(long commentId);

}
