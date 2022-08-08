package com.musseukpeople.woorimap.post.infrastructure;

import static com.musseukpeople.woorimap.post.domain.QPost.*;
import static com.musseukpeople.woorimap.post.domain.image.QPostImage.*;

import com.musseukpeople.woorimap.post.domain.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Post findPostById(Long postId) {
        return jpaQueryFactory.select(post)
            .from(post)
            .innerJoin(post.postImages.postImages, postImage)
            .fetchJoin()
            .where(
                post.id.eq(postId)
            )
            .distinct()
            .fetchOne();
    }
}
