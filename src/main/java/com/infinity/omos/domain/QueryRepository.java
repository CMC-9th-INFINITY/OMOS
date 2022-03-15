package com.infinity.omos.domain;


import com.infinity.omos.domain.Posts.Posts;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.infinity.omos.domain.Posts.QPosts.posts;
import static com.infinity.omos.domain.QFollow.follow;
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

    public List<Posts> findPostsByCategory(Category category, int size) {
        return queryFactory.selectFrom(posts).where(posts.category.eq(category), posts.isPublic.eq(true)).limit(size).orderBy(posts.createdDate.desc()).fetch();
    }

    public List<Posts> findPostsByUserId(User userId) {
        return queryFactory.selectFrom(posts).where(posts.userId.eq(userId)).orderBy(posts.id.desc()).fetch();
    }

    public List<Posts> findPublicPostsByUserId(User userId) {
        return queryFactory.selectFrom(posts).where(posts.userId.eq(userId), posts.isPublic.eq(true)).fetch();
    }

    public List<Long> findPostsIdByUserId(User userId) {
        return queryFactory.select(posts.id).from(posts).where(posts.userId.eq(userId)).fetch();
    }

    public Like findLikeByUserIdPostId(User userId, Posts posts) {
        return queryFactory.selectFrom(like).where(like.userId.eq(userId), like.postId.eq(posts)).fetchOne();
    }

    public Scrap findScrapByUserIdPostId(User userId, Posts posts) {
        return queryFactory.selectFrom(scrap).where(scrap.userId.eq(userId), scrap.postId.eq(posts)).fetchOne();
    }

    public Follow findFollowByUserId(User toUserId, User fromUserId) {
        return queryFactory.selectFrom(follow).where(follow.toUserId.eq(toUserId), follow.fromUserId.eq(fromUserId)).fetchOne();
    }

    public Boolean existsLikeByUserIdPostId(User userId, Posts postsId) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(like)
                .where(like.userId.eq(userId), like.postId.eq(postsId))
                .fetchFirst(); // limit 1

        return fetchOne != null;
    }

    public Boolean existsScarpByUserIdPostId(User userId, Posts postsId) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(scrap)
                .where(scrap.userId.eq(userId), scrap.postId.eq(postsId))
                .fetchFirst(); // limit 1

        return fetchOne != null;
    }

    public Boolean existsFollowByUserId(User fromUserId, User toUserId) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(follow)
                .where(follow.fromUserId.eq(fromUserId), follow.toUserId.eq(toUserId))
                .fetchFirst(); // limit 1

        return fetchOne != null;
    }

    public List<User> findToUserIdBYFromUserId(User fromUserId) {
        return queryFactory.select(follow.toUserId)
                .from(follow)
                .where(follow.fromUserId.eq(fromUserId))
                .orderBy(posts.createdDate.max().desc())
                .fetch();
    }

    ///여기부터 페이징처리 부분

    public List<Posts> findAllMyDj(User userId, Long postId, int pageSize) {
        return queryFactory
                .selectFrom(posts)
                .innerJoin(follow).on(posts.userId.eq(follow.toUserId))
                .innerJoin(user).on(follow.fromUserId.eq(user))
                .where(
                        ltPostId(postId),
                        user.eq(userId))
                .groupBy(posts.id)
                .orderBy(posts.id.desc())
                .limit(pageSize)
                .fetch();
    }

    public List<Posts> findAllByMusicIdByRandom(Long postId, String musicId, int pageSize) {
        return queryFactory
                .selectFrom(posts)
                .where(
                        ltPostId(postId),
                        posts.musicId.id.eq(musicId),
                        posts.isPublic.eq(true))
                .groupBy(posts.id)
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(pageSize)
                .fetch();
    }

    public List<Posts> findAllByMusicIdByCreatedDate(Long postId, String musicId, int pageSize) {
        return queryFactory
                .selectFrom(posts)
                .where(
                        ltPostId(postId),
                        posts.musicId.id.eq(musicId),
                        posts.isPublic.eq(true))
                .groupBy(posts.id)
                .orderBy(posts.id.desc())
                .limit(pageSize)
                .fetch();
    }

    public List<Posts> findAllByMusicIdByLike(Long postId, String musicId, int pageSize) {
        Long cnt = findLikeCount(postId);

        return queryFactory.selectFrom(posts)
                .leftJoin(like).on(like.postId.eq(posts))
                .where(
                        posts.musicId.id.eq(musicId)
                        , posts.isPublic.eq(true))
                .groupBy(posts.id)
                .having(
                        ltPostIdByLike(postId,cnt)
                )
                .orderBy(like.id.count().desc(),posts.id.desc())
                .limit(pageSize)
                .fetch();
    }

    private BooleanExpression ltPostId(Long postId) {
        if (postId == null) {
            return null;
        }
        return posts.id.lt(postId);
    }

    private BooleanExpression ltPostIdByLike(Long postId , Long cnt) {
        if (postId == null) {
            return null;
        }
        return  (like.id.count().eq(cnt).and(posts.id.lt(postId)))
                .or(like.id.count().lt(cnt));
    }

    public List<Posts> findAllByCategoryOrderByLike(Category category, Long postId, int pageSize) {
        Long cnt = findLikeCount(postId);

        return queryFactory.selectFrom(posts)
                .leftJoin(like).on(like.postId.eq(posts))
                .where(
                        posts.category.eq(category)
                        , posts.isPublic.eq(true))
                .groupBy(posts.id)
                .having(
                        ltPostIdByLike(postId,cnt)
                )
                .orderBy(like.id.count().desc(),posts.id.desc())
                .limit(pageSize)
                .fetch();

    }

    private Long findLikeCount(Long postId){
        return queryFactory
                .select(like.id.count())
                .from(posts)
                .leftJoin(like)
                .on(like.postId.eq(posts))
                .where(isPostId(postId))
                .fetchOne();
    }

    private BooleanExpression isPostId(Long postId) {
        if (postId == null) {
            return null;
        }
        return posts.id.eq(postId);
    }


    public List<Posts> findAllByCategoryOrderByRandom(Category category, Long postId, int pageSize) {

        return queryFactory.selectFrom(posts)
                .where(
                        ltPostId(postId),
                        posts.category.eq(category),
                        posts.isPublic.eq(true))
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(pageSize)
                .fetch();

    }

    public List<Posts> findAllByCategoryOrderByCreatedDate(Category category, Long postId, int pageSize) {

        return queryFactory.selectFrom(posts)
                .where(
                        ltPostId(postId),
                        posts.category.eq(category),
                        posts.isPublic.eq(true))
                .orderBy(posts.id.desc())
                .limit(pageSize)
                .fetch();

    }

    ///여기까지 페이징처리부분


    public String findMusicIdOnToday() {
        LocalDateTime start = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime end = LocalDate.now().minusDays(1).atTime(LocalTime.MAX);
        return queryFactory
                .select(posts.musicId.id)
                .from(posts)
                .where(
                        posts.createdDate.between(start, end)
                )
                .groupBy(posts.musicId)
                .orderBy(posts.musicId.count().desc())
                .fetchFirst();
    }

    public List<Posts> findPostsOnToday() {
        LocalDateTime start = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime end = LocalDate.now().minusDays(1).atTime(LocalTime.MAX);
        return queryFactory.selectFrom(posts)
                .leftJoin(like)
                .on(posts.id.eq(like.postId.id))
                .where(
                        posts.createdDate.between(start, end),
                        posts.isPublic.eq(true))
                .groupBy(posts.id)
                .orderBy(like.id.count().desc())
                .limit(3)
                .fetch();
    }

    public List<Long> findDjOnToday() {
        LocalDateTime start = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime end = LocalDate.now().minusDays(1).atTime(LocalTime.MAX);
        return queryFactory.select(follow.toUserId.id)
                .from(follow)
                .where(follow.createdDate.between(start, end))
                .groupBy(follow.toUserId)
                .orderBy(follow.toUserId.count().desc())
                .limit(3)
                .fetch();
    }

    public Posts findPostByRandom(Long userId) {
        return queryFactory.selectFrom(posts)
                .where(
                        posts.isPublic.eq(true),
                        posts.userId.id.eq(userId)
                )
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .fetchFirst();
    }


}
