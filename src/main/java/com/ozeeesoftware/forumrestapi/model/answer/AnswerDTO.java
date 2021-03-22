package com.ozeeesoftware.forumrestapi.model.answer;

import lombok.Data;

@Data
public class AnswerDTO {

    private String answer;
    private Long id;
    private Long userID;
    private Long commentID;

}
