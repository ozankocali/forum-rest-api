package com.ozeeesoftware.forumrestapi.model.comment;

import lombok.Data;

@Data
public class CommentDTO {

    private String comment;

    private long id;

    private long userId;

    private long postId;

}
