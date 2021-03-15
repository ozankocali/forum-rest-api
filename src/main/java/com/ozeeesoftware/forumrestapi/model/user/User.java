package com.ozeeesoftware.forumrestapi.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ozeeesoftware.forumrestapi.model.BaseModel;
import com.ozeeesoftware.forumrestapi.model.ImageModel;
import com.ozeeesoftware.forumrestapi.model.post.Post;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "users",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "userName"),
        @UniqueConstraint(columnNames = "email")
        })
public class User extends BaseModel {

    @Column
    private String userName;

    @Column
    private String email;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany
    private List<Post> posts;

    @OneToOne
    private ImageModel profileImage;

}
