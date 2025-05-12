package com.hwoolog.api.repository.post;

import com.hwoolog.api.domain.Post;
import com.hwoolog.api.request.post.PostSerch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hwoolog.api.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSerch postSerch) {
        return jpaQueryFactory.selectFrom(post)
                .offset(postSerch.getOffset())
                .orderBy(post.id.desc())
                .limit(postSerch.getSize())
                .fetch();
    }
}