package com.demos.questapp.controllers;

import com.demos.questapp.entities.Comment;
import com.demos.questapp.requests.CommentCreateRequest;
import com.demos.questapp.requests.CommentUpdateRequest;
import com.demos.questapp.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComment(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        return new ResponseEntity<>(commentService.getAllComment(userId,postId), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getOneCommentById(@PathVariable Long id){
        return new ResponseEntity<>(commentService.getOneUserById(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentCreateRequest commentCreateRequest){
        return new ResponseEntity<>(commentService.createComment(commentCreateRequest),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateCommentById(@PathVariable Long id, @RequestBody CommentUpdateRequest commentUpdateRequest){
        return new ResponseEntity<>(commentService.updateCommentById(id,commentUpdateRequest),HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long id){
        commentService.deleteCommentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
