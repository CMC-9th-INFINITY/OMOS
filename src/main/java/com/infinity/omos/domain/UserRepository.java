package com.infinity.omos.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {



    @EntityGraph(attributePaths = "authorities") //Eager조회
    Optional<User> findOneWithAuthoritiesByEmail(String email);
}
