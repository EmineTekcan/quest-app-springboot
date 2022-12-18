package com.demos.questapp.repositories;

import com.demos.questapp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserIdAndPostId(Optional<Long> userId, Optional<Long> postId);

    List<Comment> findAllByUserId(Optional<Long> userId);

    List<Comment> findAllByPostId(Optional<Long> postId);
}
