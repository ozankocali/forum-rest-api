package com.ozeeesoftware.forumrestapi.service.comment;

import com.ozeeesoftware.forumrestapi.exception.NotFoundByIdException;
import com.ozeeesoftware.forumrestapi.model.comment.Comment;
import com.ozeeesoftware.forumrestapi.model.comment.CommentDTO;
import com.ozeeesoftware.forumrestapi.repository.CommentRepository;
import com.ozeeesoftware.forumrestapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;


    @Override
    public ResponseEntity<CommentDTO> createComment(long postId, Comment comment) {
        comment.setPost(postRepository.findById(postId).get());
        CommentDTO convertedComment=convertCommentToDTO(commentRepository.save(comment));
        return ResponseEntity.ok(convertedComment);
    }

    @Override
    public ResponseEntity<CommentDTO> getCommentById(long commentId) {
        Comment existingComment=commentRepository.findById(commentId).orElseThrow(()->new NotFoundByIdException("Comment not found"));
        CommentDTO convertedComment=convertCommentToDTO(existingComment);
        return ResponseEntity.ok(convertedComment);
    }

    @Override
    public ResponseEntity<List<CommentDTO>> getAllCommentsByPostId(long postId) {
        List<Comment> allComments=commentRepository.findAllByPostId(postId);
        List<CommentDTO> allCommentsDTO=new ArrayList<>();
        for (Comment comment:allComments){
            allCommentsDTO.add(convertCommentToDTO(comment));
        }
        return ResponseEntity.ok(allCommentsDTO);
    }

    @Override
    public ResponseEntity<CommentDTO> deleteCommentById(long commentId) {
        Comment existingComment=commentRepository.findById(commentId).orElseThrow(()->new NotFoundByIdException("Comment not found"));
        existingComment.setDeleted(true);
        CommentDTO convertedComment=convertCommentToDTO(commentRepository.save(existingComment));
        return ResponseEntity.ok(convertedComment);

    }


    private CommentDTO convertCommentToDTO(Comment comment){
        if(comment!=null){
            CommentDTO commentDTO=new CommentDTO();
            commentDTO.setComment(comment.getComment());
            commentDTO.setPostId(comment.getPost().getId());
            commentDTO.setId(comment.getUser().getId());
            commentDTO.setUserId(comment.getUser().getId());
            return commentDTO;
        }
        return null;
    }


}
