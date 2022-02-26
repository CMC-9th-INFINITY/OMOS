package com.infinity.omos.domain.Posts;

import com.infinity.omos.domain.Category;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.infinity.omos.domain.Posts.QPosts.posts;

@RequiredArgsConstructor
public class PostsRepositoryImpl implements PostsRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Posts> findAllByCategory(Category category, Pageable pageable){
        QueryResults<Posts> results = queryFactory
                .selectFrom(posts)
                .where(posts.category.eq(category),posts.publicOrNot.eq(true))
                .offset(pageable.getOffset())   //N 번부터 시작
                .limit(pageable.getPageSize()) //조회 갯수
                .fetchResults();
        long total = results.getTotal();
        List<Posts> posts = results.getResults();
        return new PageImpl<>(posts,pageable,total);
    }
}
