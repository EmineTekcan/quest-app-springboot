package com.demos.questapp.controllers;

import com.demos.questapp.entities.Post;
import com.demos.questapp.requests.PostCreateRequest;
import com.demos.questapp.requests.PostUpdateRequest;
import com.demos.questapp.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> getAllPost(@RequestParam Optional<Long> userId){
        return new ResponseEntity<>(postService.getAllPost(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getOnePostById(@PathVariable Long id){
        return new ResponseEntity<>(postService.getOnePostById(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostCreateRequest postCreateRequest){
        return new ResponseEntity<>(postService.createPost(postCreateRequest),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody PostUpdateRequest postUpdateRequest){
        return new ResponseEntity<>(postService.updatePostById(id,postUpdateRequest),HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id){
        postService.deletePostById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
