package com.infinity.omos;

import com.infinity.omos.api.RequestGeniusApi;
import com.infinity.omos.api.SpotifyAllSearchApi;
import com.infinity.omos.api.SpotifyApiAuthorization;
import com.infinity.omos.domain.QueryRepository;
import com.infinity.omos.domain.User.UserRepository;
import com.infinity.omos.dto.AlbumTrackDto;
import com.infinity.omos.dto.HotTrackDto;
import com.infinity.omos.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.michaelthelin.spotify.SpotifyApi;

import java.io.IOException;
import java.util.List;




@SpringBootTest
public class GeniusRequestApiTests {

//    @Autowired
//    SpotifyApiAuthorization spotifyApiAuthorization;
//
//    @Autowired
//    AuthService authService;
//
//    @Autowired
//    QueryRepository queryRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Test
//    public void requestGeniusSearchApi() throws IOException {
//        RequestGeniusApi geniusRequestApi = new RequestGeniusApi();
//        geniusRequestApi.requestGeniusSearchApi("장범준");
//
//    }
//
//    @Test
//    public void ApiTests() {
//        SpotifyApi spotifyApi = spotifyApiAuthorization.clientCredentials_Sync();
//        List<AlbumTrackDto> albumTrackDtos = SpotifyAllSearchApi.getAlbumTrackApi(spotifyApi.getAccessToken(),"01dPJcwyht77brL4JQiR8R");
//        System.out.println(albumTrackDtos.get(1).getMusicTitle());
//        //System.out.println(trackDto.getArtists());
//
//    }
//
//    @Test
//    public void test() {
//        SpotifyApi spotifyApi = spotifyApiAuthorization.clientCredentials_Sync();
//        List<HotTrackDto> trackDtos = SpotifyAllSearchApi.getHotTracksApi(spotifyApi.getAccessToken(),"3HqSLMAZ3g3d5poNaI7GOU");
//        for(HotTrackDto trackDto : trackDtos){
//            System.out.println(trackDto.getMusicTitle());
//        }
//
//    }
//
//    @Test
//    public void repositoryTest(){
//
//    }
}

