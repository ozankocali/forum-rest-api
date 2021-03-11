package com.ozeeesoftware.forumrestapi.service;

import com.ozeeesoftware.forumrestapi.model.post.Post;
import org.springframework.http.ResponseEntity;

public interface IPostService {

    ResponseEntity createPost(Post post);
    ResponseEntity updatePost(Post post);
    ResponseEntity getAllPosts();
    ResponseEntity getPostById(long id);
    ResponseEntity deletePostById(long id);



}
