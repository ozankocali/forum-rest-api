package com.ozeeesoftware.forumrestapi.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ozeeesoftware.forumrestapi.model.BaseModel;
import com.ozeeesoftware.forumrestapi.model.image.ImageModel;
import com.ozeeesoftware.forumrestapi.model.post.Post;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    private String name;
    private String surname;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private String password;

    private boolean resetPassword;

    @JsonIgnore
    private String verificationCode;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany
    private List<Post> posts;

    @OneToOne
    private ImageModel profileImage;

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

}
