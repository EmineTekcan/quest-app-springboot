package com.demos.questapp.repositories;

import com.demos.questapp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUserId(Long user_id);
}
