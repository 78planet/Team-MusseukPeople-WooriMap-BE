package com.musseukpeople.woorimap.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musseukpeople.woorimap.post.infrastructure.PostQueryRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryRepository {
}
