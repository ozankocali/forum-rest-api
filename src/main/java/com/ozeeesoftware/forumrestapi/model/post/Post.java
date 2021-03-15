package com.ozeeesoftware.forumrestapi.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ozeeesoftware.forumrestapi.model.BaseModel;
import com.ozeeesoftware.forumrestapi.model.post.PostStatus;
import com.ozeeesoftware.forumrestapi.model.user.User;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@Table(name = "posts")
public class Post extends BaseModel {

    private String subject;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;



}
