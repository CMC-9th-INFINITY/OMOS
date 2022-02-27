package com.infinity.omos;

import com.infinity.omos.api.RequestGeniusApi;
import com.infinity.omos.api.SpotifyAllSearchApi;
import com.infinity.omos.api.SpotifyApiAuthorization;
import com.infinity.omos.dto.AlbumDto;
import com.infinity.omos.dto.TrackDto;
import com.infinity.omos.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.michaelthelin.spotify.SpotifyApi;

import java.io.IOException;
import java.util.List;




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
        TrackDto trackDto = SpotifyAllSearchApi.getTrackApi(spotifyApi.getAccessToken(),"5xrtzzzikpG3BLbo4q1Yul");
        System.out.println(trackDto.getMusicTitle());
        System.out.println(trackDto.getArtists());

    }

    @Test
    public void test() {
        SpotifyApi spotifyApi = spotifyApiAuthorization.clientCredentials_Sync();
        List<AlbumDto> trackDtos = SpotifyAllSearchApi.spotifyAlbumSearchApi(spotifyApi.getAccessToken(),"샴푸");
  //      for(AlbumDto trackDto : trackDtos){
//            System.out.println(trackDto.getMusicTitle());

    //    }
    }
}

