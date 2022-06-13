package com.infinity.omos.domain.Block;

import com.infinity.omos.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Long> {
    Optional<Block> findByFromUserIdAndToUserId(User fromUserId, User toUseId);
}
