package com.infinity.omos.domain;

import com.infinity.omos.domain.Posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {
    boolean existsByPostId(Posts postsId);
    int countByPostId(Posts postId);
    void deleteAllByPostId(Posts postsId);
    void deleteAllByUserId(User userId);

}
