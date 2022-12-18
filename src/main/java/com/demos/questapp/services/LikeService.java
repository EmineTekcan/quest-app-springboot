package com.demos.questapp.services;

import com.demos.questapp.advice.ItemAlreadyExistException;
import com.demos.questapp.advice.ItemNotFoundException;
import com.demos.questapp.entities.Like;
import com.demos.questapp.entities.Post;
import com.demos.questapp.entities.User;
import com.demos.questapp.repositories.LikeRepository;
import com.demos.questapp.repositories.PostRepository;
import com.demos.questapp.repositories.UserRepository;
import com.demos.questapp.requests.LikeCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public List<Like> getAllLikeByUserIdAndPostId(Optional<Long> userId, Optional<Long> postId) {
        if (userId.isPresent() && postId.isPresent())
            return likeRepository.findByUserIdAndPostId(userId,postId);
        else if (userId.isPresent()) {
            return likeRepository.findAllByUserId(userId);
        } else if (postId.isPresent()) {
            return likeRepository.findAllByPostId(postId);
        }else
            return likeRepository.findAll();
    }


    public Like getLikeById(Long id) {
        return likeRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Like not found with id: "+id));
    }

    public Like createLike(LikeCreateRequest likeCreateRequest) {
        Like like=likeRepository.findById(likeCreateRequest.getId()).orElse(null);
        User user=userRepository.findById(likeCreateRequest.getUserId())
                .orElseThrow(()->new ItemNotFoundException("Not found user with id: "+likeCreateRequest.getUserId()));
        Post post=postRepository.findById(likeCreateRequest.getPostId())
                .orElseThrow(()->new ItemNotFoundException("Not found post with id"+likeCreateRequest.getPostId()));
        if (like !=null)
            throw new ItemAlreadyExistException("Like already exist with id: "+likeCreateRequest.getId());
        Like createLike=new Like();
        createLike.setId(likeCreateRequest.getId());
        createLike.setPost(post);
        createLike.setUser(user);
        return likeRepository.save(createLike);
    }


    public void deleteLikeById(Long id) {
        likeRepository.deleteById(id);
    }
}
