package com.ozeeesoftware.forumrestapi.service.post;

import com.ozeeesoftware.forumrestapi.exception.NotFoundByIdException;
import com.ozeeesoftware.forumrestapi.model.post.Post;
import com.ozeeesoftware.forumrestapi.model.post.PostStatus;
import com.ozeeesoftware.forumrestapi.repository.PostRepository;
import com.ozeeesoftware.forumrestapi.service.post.IPostService;
import com.ozeeesoftware.forumrestapi.util.NullAwareBeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService implements IPostService {

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
    public ResponseEntity<Post> getPostById(long postId) {
        Post existingPost=postRepository.findById(postId).orElseThrow(()->new NotFoundByIdException("Post not found"));
        return ResponseEntity.ok(existingPost);
    }

    @Override
    public ResponseEntity<Post> deletePostById(long postId) {
        Post existingPost=postRepository.findById(postId).orElseThrow(()->new NotFoundByIdException("Post not found"));
        existingPost.setDeleted(true);
        postRepository.save(existingPost);
        return ResponseEntity.ok(existingPost);
    }

    @Override
    public ResponseEntity<Post> approvePost(long postId) {
        Post existingPost=postRepository.findById(postId).orElseThrow(()->new NotFoundByIdException("Post not found"));
        existingPost.setPostStatus(PostStatus.APPROVED);
        return ResponseEntity.ok(postRepository.save(existingPost));
    }

    @Override
    public ResponseEntity approveAll() {
        postRepository.findAll().stream().filter(post -> post.getPostStatus().equals(PostStatus.PENDING))
                .forEach(post -> {
                    post.setPostStatus(PostStatus.APPROVED);
                    postRepository.save(post);
                });
        return ResponseEntity.ok("All posts are approved");
    }

    @Override
    public ResponseEntity<Post> rejectPost(long postId) {
        Post existingPost=postRepository.findById(postId).orElseThrow(()->new NotFoundByIdException("Post not found"));
        existingPost.setPostStatus(PostStatus.REJECTED);
        return ResponseEntity.ok(postRepository.save(existingPost));
    }

    @Override
    public ResponseEntity rejectAll() {
        postRepository.findAll().stream().filter(post -> post.getPostStatus().equals(PostStatus.PENDING))
                .forEach(post -> {
                    post.setPostStatus(PostStatus.REJECTED);
                    postRepository.save(post);
                });
        return ResponseEntity.ok("All posts are rejected");
    }

    @Override
    public ResponseEntity<List<Post>> approvedPosts() {
        List<Post> approvedPosts= postRepository.findAll().stream().filter(post -> post.getPostStatus().equals(PostStatus.APPROVED)).collect(Collectors.toList());
        return ResponseEntity.ok(approvedPosts);

    }
}
