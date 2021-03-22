package com.ozeeesoftware.forumrestapi.service.answer;

import com.ozeeesoftware.forumrestapi.model.answer.Answer;
import com.ozeeesoftware.forumrestapi.model.answer.AnswerDTO;
import com.ozeeesoftware.forumrestapi.repository.AnswerRepository;
import com.ozeeesoftware.forumrestapi.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerService implements AnswerServiceImpl{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public ResponseEntity<AnswerDTO> createAnswer(long commentId, Answer answer) {
        answer.setComment(commentRepository.findById(commentId).get());
        return ResponseEntity.ok(convertAnswerToDTO(answerRepository.save(answer)));
    }

    @Override
    public ResponseEntity<AnswerDTO> getAnswerById(long answerId) {
        Answer answer=answerRepository.findById(answerId).orElse(null);
        return ResponseEntity.ok(convertAnswerToDTO(answer));
    }

    @Override
    public ResponseEntity getAllAnswersByCommentId(long commentId) {
        List<Answer> answerList=answerRepository.findAllByCommentId(commentId);
        List<AnswerDTO> answerDTOS=new ArrayList<>();

        for (Answer answer:answerList){
            answerDTOS.add(convertAnswerToDTO(answer));
        }
        return ResponseEntity.ok(answerDTOS);
    }

    @Override
    public ResponseEntity deleteAnswerById(long answerId) {
        Answer answer=answerRepository.findById(answerId).orElse(null);
        if(answer==null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        answer.setDeleted(true);
        return ResponseEntity.ok(convertAnswerToDTO(answerRepository.save(answer)));
    }

    private AnswerDTO convertAnswerToDTO(Answer answer){
        if(answer!=null){
            AnswerDTO answerDTO=new AnswerDTO();
            answerDTO.setAnswer(answer.getAnswer());
            answerDTO.setCommentID(answer.getComment().getId());
            answerDTO.setId(answer.getUser().getId());
            answerDTO.setUserID(answer.getUser().getId());
            return answerDTO;
        }
        return null;
    }

}
