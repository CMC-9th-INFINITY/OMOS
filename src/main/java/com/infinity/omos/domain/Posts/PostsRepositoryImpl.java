package com.infinity.omos.domain.Posts;

import com.infinity.omos.domain.Category;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.infinity.omos.domain.Posts.QPosts.posts;
import static com.infinity.omos.domain.QLike.like;

@RequiredArgsConstructor
public class PostsRepositoryImpl implements PostsRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Posts> findAllByCategory(Category category, Pageable pageable){
        QueryResults<Posts> results = queryFactory
                .select(posts)
                .from(posts)
                .where(posts.category.eq(category),posts.isPublic.eq(true))
                .offset(pageable.getOffset())   //N 번부터 시작
                .limit(pageable.getPageSize()) //조회 갯수
                .orderBy()
                .fetchResults();
        long total = results.getTotal();

        List<Posts> posts = results.getResults();
        return new PageImpl<>(posts,pageable,total);
    }

    private OrderSpecifier<?> postsSort(Pageable page) {
        //서비스에서 보내준 Pageable 객체에 정렬조건 null 값 체크
        if (!page.getSort().isEmpty()) {
            //정렬값이 들어 있으면 for 사용하여 값을 가져온다
            for (Sort.Order order : page.getSort()) {
                // 서비스에서 넣어준 DESC or ASC 를 가져온다.
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                // 서비스에서 넣어준 정렬 조건을 스위치 케이스 문을 활용하여 셋팅하여 준다.
                switch (order.getProperty()){
                    case "viewsCount":
                        return new OrderSpecifier(direction, NumberExpression.random());
                    //case "likeCount":
                    //    return new OrderSpecifier(direction, like.postId.id.eq(posts.id).count());
                    case "createdDate":
                        return new OrderSpecifier(direction, posts.createdDate);
                }
            }
        }
        return null;
    }


    @Override
    public Page<Posts> findAllByCategoryOrderByLike(Category category, Pageable pageable){
        QueryResults<Posts> results =     queryFactory.selectFrom(posts)
                .leftJoin(like).on(like.postId.eq(posts))
                .where(
                        posts.category.eq(category)
                        , posts.isPublic.eq(true))
                .groupBy(posts.id)
                .orderBy(like.id.count().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        long total = results.getTotal();

        List<Posts> posts = results.getResults();
        return new PageImpl<>(posts,pageable,total);
    }












}
