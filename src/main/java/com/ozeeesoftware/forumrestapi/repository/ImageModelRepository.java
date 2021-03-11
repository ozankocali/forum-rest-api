package com.ozeeesoftware.forumrestapi.repository;

import com.ozeeesoftware.forumrestapi.model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageModelRepository extends JpaRepository<ImageModel,Long> {
}
