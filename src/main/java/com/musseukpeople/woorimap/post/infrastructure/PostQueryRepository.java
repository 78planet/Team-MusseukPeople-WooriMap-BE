package com.musseukpeople.woorimap.post.infrastructure;

import com.musseukpeople.woorimap.post.domain.Post;

public interface PostQueryRepository {

    Post findPostById(Long postId);
}
