package com.infinity.omos.domain.RefreshToken;

import com.infinity.omos.domain.RefreshToken.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByUserEmail(String userEmail);

}