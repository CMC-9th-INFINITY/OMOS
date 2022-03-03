package com.infinity.omos.domain;

import com.infinity.omos.domain.Posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap,Long> {
    boolean existsByUserId(User userId);
    int countByPostId(Posts postId);


}
