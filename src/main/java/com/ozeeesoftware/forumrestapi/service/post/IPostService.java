package com.ozeeesoftware.forumrestapi.service.post;

import com.ozeeesoftware.forumrestapi.model.post.Post;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.InvocationTargetException;

public interface IPostService {

    ResponseEntity createPost(Post post);
    ResponseEntity updatePost(Post post)throws InvocationTargetException, IllegalAccessException;
    ResponseEntity getAllPosts();
    ResponseEntity getPostById(long postId);
    ResponseEntity deletePostById(long postId);
    ResponseEntity approvePost(long postId);
    ResponseEntity approveAll();
    ResponseEntity rejectPost(long postId);
    ResponseEntity rejectAll();
    ResponseEntity approvedPosts();

}
