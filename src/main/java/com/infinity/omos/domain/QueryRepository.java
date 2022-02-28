package com.infinity.omos.domain;


import com.infinity.omos.domain.Posts.Posts;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.infinity.omos.domain.Posts.QPosts.posts;
import static com.infinity.omos.domain.QLike.like;
import static com.infinity.omos.domain.QScrap.scrap;
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
        return queryFactory.selectFrom(posts).where(posts.category.eq(category),posts.isPublic.eq(true)).limit(size).orderBy(posts.createdDate.desc()).fetch();
    }

    public List<Posts> findPostsByUserId(User userId){
        return queryFactory.selectFrom(posts).where(posts.userId.eq(userId)).fetch();
    }

    public Like findLikeByUserIdPostId(User userId, Posts posts){
        return queryFactory.selectFrom(like).where(like.userId.eq(userId),like.postId.eq(posts)).fetchOne();
    }

    public Scrap findScrapByUserIdPostId(User userId, Posts posts){
        return queryFactory.selectFrom(scrap).where(scrap.userId.eq(userId),scrap.postId.eq(posts)).fetchOne();
    }

    public Boolean existsLikeByUserIdPostId(User userId, Posts postsId){
        Integer fetchOne = queryFactory
                .selectOne()
                .from(like)
                .where(like.userId.eq(userId),like.postId.eq(postsId))
                .fetchFirst(); // limit 1

        return fetchOne != null;
    }

    public Boolean existsScarpByUserIdPostId(User userId, Posts postsId){
        Integer fetchOne = queryFactory
                .selectOne()
                .from(scrap)
                .where(scrap.userId.eq(userId),scrap.postId.eq(postsId))
                .fetchFirst(); // limit 1

        return fetchOne != null;
    }





}
