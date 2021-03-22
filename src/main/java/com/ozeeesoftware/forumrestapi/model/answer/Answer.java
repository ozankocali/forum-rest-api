package com.ozeeesoftware.forumrestapi.model.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ozeeesoftware.forumrestapi.model.BaseModel;
import com.ozeeesoftware.forumrestapi.model.comment.Comment;
import com.ozeeesoftware.forumrestapi.model.post.Post;
import com.ozeeesoftware.forumrestapi.model.user.User;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@Where(clause = "deleted=false")
@Table(name = "comments")
public class Answer extends BaseModel {

    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "user_id",referencedColumnName = "id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "post_id",referencedColumnName = "id",nullable = false)
    private Comment comment;

}
