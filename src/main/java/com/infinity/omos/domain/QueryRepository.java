package com.infinity.omos.domain;


import com.infinity.omos.domain.Posts.Posts;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.infinity.omos.domain.Posts.QPosts.posts;
import static com.infinity.omos.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class QueryRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * email로 id가져오기
     *
     * @param email
     * @return
     */
    public Long findUserIdByUserEmail(String email) {
        return queryFactory.select(user.id).from(user).where(user.email.eq(email)).fetchOne();
    }

    public List<Posts> findPostsByCategory(Category category, int size){
        return queryFactory.selectFrom(posts).where(posts.category.eq(category),posts.publicOrNot.eq(true)).limit(size).orderBy(posts.createdDate.desc()).fetch();
    }


}
