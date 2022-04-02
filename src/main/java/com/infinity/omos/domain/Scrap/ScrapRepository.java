package com.infinity.omos.domain.Scrap;

import com.infinity.omos.domain.Posts.Posts;
import com.infinity.omos.domain.Scrap.Scrap;
import com.infinity.omos.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap,Long> {
    boolean existsByPostId(Posts postsId);
    int countByPostId(Posts postId);
    void deleteAllByPostId(Posts postsId);
    void deleteAllByUserId(User userId);


}
