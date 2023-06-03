package com.infinity.omos.api;

import com.infinity.omos.api.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static com.infinity.omos.consts.ApisConst.*;

@Service
@RequiredArgsConstructor
public class SpotifyAuthService {

    private final SpotifyApiClient spotifyApiClient;
    private final SpotifyTokenRepository spotifyTokenRepository;

    public String getToken() {

        Optional<SpotifyToken> optionalSpotifyToken = spotifyTokenRepository.findById(SPOTIFY_TOKEN_ID);
        SpotifyToken spotifyToken;
        if (optionalSpotifyToken.isEmpty() || optionalSpotifyToken.get().getExpirationTime().isAfter(LocalDateTime.now())) {

            TokenResponse tokenResponse = spotifyApiClient.getToken(
                    Collections.singletonMap(GRANT_TYPE, SPOTIFY_GRANT_TYPE)
            );
            spotifyToken = SpotifyToken.builder()
                    .token(tokenResponse.getAccessToken())
                    .expirationTime(LocalDateTime.now().plusSeconds(tokenResponse.getExpiresIn()))
                    .build();
            spotifyTokenRepository.save(spotifyToken);
        } else {
            spotifyToken = optionalSpotifyToken.get();

        }

        return spotifyToken.getToken();


    }


}
