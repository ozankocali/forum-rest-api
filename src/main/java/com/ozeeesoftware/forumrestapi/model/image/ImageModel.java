package com.ozeeesoftware.forumrestapi.model.image;


import com.ozeeesoftware.forumrestapi.model.BaseModel;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Where(clause = "deleted = false")
@Data
@Entity
@Table(name = "images")
public class ImageModel extends BaseModel {

    @Column(name = "imageName")
    private String name;
    @Column(name = "imageUrl")
    private String url;

}
