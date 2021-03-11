package com.ozeeesoftware.forumrestapi.repository;

import com.ozeeesoftware.forumrestapi.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
}
