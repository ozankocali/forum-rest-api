package com.ozeeesoftware.forumrestapi.model.user;

import com.ozeeesoftware.forumrestapi.model.BaseModel;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
@Where(clause = "deleted = false")
public class UserDTO extends BaseModel implements Serializable {

    private static final long serialVersionUID=4479809601898895873L;

    private String name;
    private String surname;
    private String email;
    private String mobile;
    private String password;

    private boolean resetPassword;

}
