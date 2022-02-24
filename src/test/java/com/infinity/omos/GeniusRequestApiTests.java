package com.infinity.omos;

import com.infinity.omos.api.RequestGeniusApi;
import com.infinity.omos.api.SpotifyAllSearchApi;
import com.infinity.omos.api.SpotifyApiAuthorization;
import com.infinity.omos.api.SpotifySearchApi;
import com.infinity.omos.domain.ProviderType;
import com.infinity.omos.dto.AlbumsDto;
import com.infinity.omos.dto.SnsLoginDto;
import com.infinity.omos.dto.TokenDto;
import com.infinity.omos.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.michaelthelin.spotify.SpotifyApi;

import java.io.IOException;
import java.util.List;


import static com.infinity.omos.api.SpotifySearchApi.searchAlbums_Sync;

@SpringBootTest
public class GeniusRequestApiTests {

    @Autowired
    SpotifyApiAuthorization spotifyApiAuthorization;

    @Autowired
    AuthService authService;

    @Test
    public void requestGeniusSearchApi() throws IOException {
        RequestGeniusApi geniusRequestApi = new RequestGeniusApi();
        geniusRequestApi.requestGeniusSearchApi("장범준");

    }

    @Test
    public void ApiTests() {
        SpotifyApi spotifyApi = spotifyApiAuthorization.clientCredentials_Sync();
        List<AlbumsDto> albumsDtos = searchAlbums_Sync(spotifyApi, "샴푸");
        for(AlbumsDto albumsDto : albumsDtos){
            System.out.println(albumsDto.getTitle());
        }
    }

    @Test
    public void test() {
        SpotifyApi spotifyApi = spotifyApiAuthorization.clientCredentials_Sync();
        List<AlbumsDto> albumsDtos = SpotifyAllSearchApi.requestGeniusSearchApi(spotifyApi.getAccessToken(),"track","샴푸");
        for(AlbumsDto albumsDto : albumsDtos){
            System.out.println(albumsDto.getTitle());
            System.out.println(albumsDto.getArtist());
        }
    }
}
