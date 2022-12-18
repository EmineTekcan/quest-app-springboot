package com.demos.questapp.services;

import com.demos.questapp.advice.ItemAlreadyExistException;
import com.demos.questapp.advice.ItemNotFoundException;
import com.demos.questapp.entities.Comment;
import com.demos.questapp.entities.Post;
import com.demos.questapp.entities.User;
import com.demos.questapp.repositories.CommentRepository;
import com.demos.questapp.repositories.PostRepository;
import com.demos.questapp.repositories.UserRepository;
import com.demos.questapp.requests.CommentCreateRequest;
import com.demos.questapp.requests.CommentUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public List<Comment> getAllComment(Optional<Long> userId, Optional<Long> postId) {
        if (userId.isPresent() && postId.isPresent())
            return commentRepository.findByUserIdAndPostId(userId,postId);
        else if (userId.isPresent()) {
            return commentRepository.findAllByUserId(userId);
        } else if (postId.isPresent()) {
            return commentRepository.findAllByPostId(postId);
        }else
            return commentRepository.findAll();

    }

    public Comment getOneUserById(Long id) {
        return commentRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Comment not found with id: "+id));
    }

    public Comment updateCommentById(Long id, CommentUpdateRequest commentUpdateRequest) {
        Comment comment=commentRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Comment not found with id: "+id));
        comment.setText(commentUpdateRequest.getText());
        commentRepository.save(comment);
        return comment;
    }

    public Comment createComment(CommentCreateRequest commentCreateRequest) {
        Comment comment=commentRepository.findById(commentCreateRequest.getId()).orElse(null);
        User user=userRepository.findById(commentCreateRequest.getUserId()).
                orElseThrow(()->new ItemNotFoundException("User not found with id: "+commentCreateRequest.getUserId()));
        Post post=postRepository.findById(commentCreateRequest.getPostId()).
                orElseThrow(()->new ItemNotFoundException("Post not found with id: "+commentCreateRequest.getPostId()));
        if (comment !=null)
            throw new ItemAlreadyExistException("Comment already found with id: "+commentCreateRequest.getId());

        Comment createComment=new Comment();
        createComment.setId(commentCreateRequest.getId());
        createComment.setText(commentCreateRequest.getText());
        createComment.setUser(user);
        createComment.setPost(post);
        commentRepository.save(createComment);
        return createComment;
    }

    public void deleteCommentById(Long id) {
        Comment comment=commentRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Comment not found with id: "+id));
        commentRepository.deleteById(id);

    }
}
