package com.demos.questapp.controllers;

import com.demos.questapp.entities.Like;
import com.demos.questapp.requests.LikeCreateRequest;
import com.demos.questapp.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping
    public ResponseEntity<List<Like>> getAllLike(@RequestParam Optional<Long> userId,@RequestParam Optional<Long> postId ){
        return new ResponseEntity<>(likeService.getAllLikeByUserIdAndPostId(userId,postId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Like> getLikeById(@PathVariable Long id){
        return new ResponseEntity<>(likeService.getLikeById(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Like> createLike(@RequestBody LikeCreateRequest likeCreateRequest){
        return new ResponseEntity<>(likeService.createLike(likeCreateRequest),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLikeById(@PathVariable Long id){
        likeService.deleteLikeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
