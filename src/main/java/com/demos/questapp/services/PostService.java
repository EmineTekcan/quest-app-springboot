package com.demos.questapp.services;

import com.demos.questapp.advice.ItemAlreadyExistException;
import com.demos.questapp.advice.ItemNotFoundException;
import com.demos.questapp.entities.Post;
import com.demos.questapp.entities.User;
import com.demos.questapp.repositories.PostRepository;
import com.demos.questapp.repositories.UserRepository;
import com.demos.questapp.requests.PostCreateRequest;
import com.demos.questapp.requests.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    public List<Post> getAllPost(Optional<Long> userId) {
        if (userId.isPresent())
            return postRepository.findAllByUserId(userId.get());
        return postRepository.findAll();
    }

    public Post getOnePostById(Long id) {
        return postRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Not found post with id: "+id));
    }

    public Post createPost(PostCreateRequest postCreateRequest) {
        User user=userService.getOneUserById(postCreateRequest.getUserId());
        Post post=postRepository.findById(postCreateRequest.getId()).orElse(null);
        if (post !=null)
            throw new ItemAlreadyExistException("Post already exist with id: "+postCreateRequest.getId());
        Post createPost=new Post();
        createPost.setId(postCreateRequest.getId());
        createPost.setText(postCreateRequest.getText());
        createPost.setTitle(postCreateRequest.getTitle());
        createPost.setUser(user);
        postRepository.save(createPost);
        return createPost;
    }

    public Post updatePostById(Long id, PostUpdateRequest postUpdateRequest) {
        Post foundPost=postRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Not found post with id: "+id));
        foundPost.setText(postUpdateRequest.getText());
        foundPost.setTitle(postUpdateRequest.getTitle());
        postRepository.save(foundPost);
        return foundPost;
    }

    public void deletePostById(Long id) {
        Post foundPost=postRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Not found post with id: "+id));
        if (foundPost !=null)
            postRepository.deleteById(id);

    }
}
