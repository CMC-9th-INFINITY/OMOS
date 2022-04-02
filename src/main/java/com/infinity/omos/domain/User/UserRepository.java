package com.infinity.omos.domain.User;

import com.infinity.omos.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByPassword(String password);

    boolean existsByNickname(String nickName);
}
