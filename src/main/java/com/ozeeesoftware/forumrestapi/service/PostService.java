package com.ozeeesoftware.forumrestapi.service;

import com.ozeeesoftware.forumrestapi.exception.NotFoundByIdException;
import com.ozeeesoftware.forumrestapi.model.post.Post;
import com.ozeeesoftware.forumrestapi.model.post.PostStatus;
import com.ozeeesoftware.forumrestapi.repository.PostRepository;
import com.ozeeesoftware.forumrestapi.util.NullAwareBeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService implements IPostService{

    @Autowired
    private PostRepository postRepository;

    @Override
    public ResponseEntity<Post> createPost(Post post) {
        post.setPostStatus(PostStatus.PENDING);
        return ResponseEntity.ok(postRepository.save(post));
    }

    @Override
    public ResponseEntity<Post> updatePost(Post post)throws InvocationTargetException, IllegalAccessException {
        Post existingPost=postRepository.findById(post.getId()).orElseThrow(()->new NotFoundByIdException("Post not found"));
        if(existingPost==null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        BeanUtilsBean exists=new NullAwareBeanUtilsBean();
        exists.copyProperties(existingPost,post);
        return ResponseEntity.ok(postRepository.save(existingPost));
    }

    @Override
    public ResponseEntity<List<Post>> getAllPosts() {

        return ResponseEntity.ok(postRepository.findAll());

    }

    @Override
    public ResponseEntity<Post> getPostById(long id) {
        Post existingPost=postRepository.findById(id).orElseThrow(()->new NotFoundByIdException("Post not found"));
        return ResponseEntity.ok(existingPost);
    }

    @Override
    public ResponseEntity deletePostById(long id) {
        Post existingPost=postRepository.findById(id).orElseThrow(()->new NotFoundByIdException("Post not found"));
        existingPost.setDeleted(true);
        postRepository.save(existingPost);
        return ResponseEntity.ok(existingPost);
    }
}
