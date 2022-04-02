package com.infinity.omos.domain.Follow;

import com.infinity.omos.domain.Follow.Follow;
import com.infinity.omos.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow,Long> {
    List<Follow> findByFromUserId(User fromUserId);
    int countByToUserId(User toUserId);
    int countByFromUserId(User fromUserId);
    void deleteAllByFromUserId(User fromUserId);
    void deleteAllByToUserId(User toUserId);

}
