package com.infinity.omos.api;

import com.infinity.omos.api.dto.TokenResponse;
import com.infinity.omos.config.SpotifyAuthConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "spotify", url = "https://accounts.spotify.com", configuration = SpotifyAuthConfiguration.class)
public interface SpotifyApiClient {

    @PostMapping(value = "/api/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenResponse getToken(@RequestBody Map<String, ?> grantType);
}
