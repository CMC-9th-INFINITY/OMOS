package com.infinity.omos.controller;

import com.infinity.omos.api.SpotifyAllSearchApi;
import com.infinity.omos.api.SpotifyApiAuthorization;
import com.infinity.omos.dto.AlbumDto;
import com.infinity.omos.dto.AlbumTrackDto;
import com.infinity.omos.dto.ArtistDto;
import com.infinity.omos.dto.TrackDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.michaelthelin.spotify.SpotifyApi;

import java.util.List;

@Controller
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Api(tags = {"검색관련 API"})
public class SearchController {
    private final SpotifyApiAuthorization spotifyApiAuthorization;

    @ApiOperation(value = "앨범검색API", notes = "limit은 한번에 불러올 데이터 개수, offset은 0부터 시작이고 페이지*limit 이라고 보시면 됩니당. 즉, 내가 보는 페이지에서 첫번째 게시글의 인덱스...?  \n 예를들어 limit이 5이고 2번째 페이지를 보고 싶으면 offset은 10, limit이 3이고 0번째 페이지를 보고 싶으시면 offset은 0")
    @GetMapping("/album")
    public ResponseEntity<List<AlbumDto>> albumSearch(@RequestParam("keyword")String keyword, @RequestParam("offset")int offset, @RequestParam("limit")int limit) {
        SpotifyApi spotifyApi = spotifyApiAuthorization.clientCredentials_Sync();
        return ResponseEntity.ok(SpotifyAllSearchApi.spotifyAlbumSearchApi(spotifyApi.getAccessToken(), keyword, offset, limit));
    }

    @ApiOperation(value = "노래검색API", notes = "limit은 한번에 불러올 데이터 개수, offset은 0부터 시작이고 페이지*limit 이라고 보시면 됩니당. 즉, 내가 보는 페이지에서 첫번째 게시글의 인덱스...?  \n 예를들어 limit이 5이고 2번째 페이지를 보고 싶으면 offset은 10, limit이 3이고 0번째 페이지를 보고 싶으시면 offset은 0")
    @GetMapping("/track")
    public ResponseEntity<List<TrackDto>> trackSearch(@RequestParam("keyword")String keyword, @RequestParam("offset")int offset, @RequestParam("limit")int limit) {
        SpotifyApi spotifyApi = spotifyApiAuthorization.clientCredentials_Sync();
        return ResponseEntity.ok(SpotifyAllSearchApi.spotifyTrackSearchApi(spotifyApi.getAccessToken(), keyword, offset, limit));
    }

    @ApiOperation(value = "가수검색API", notes = "limit은 한번에 불러올 데이터 개수, offset은 0부터 시작이고 페이지*limit 이라고 보시면 됩니당. 즉, 내가 보는 페이지에서 첫번째 게시글의 인덱스...?  \n 예를들어 limit이 5이고 2번째 페이지를 보고 싶으면 offset은 10, limit이 3이고 0번째 페이지를 보고 싶으시면 offset은 0")
    @GetMapping("/artist")
    public ResponseEntity<List<ArtistDto>> artistSearch(@RequestParam("keyword")String keyword, @RequestParam("offset")int offset, @RequestParam("limit")int limit) {
        SpotifyApi spotifyApi = spotifyApiAuthorization.clientCredentials_Sync();
        return ResponseEntity.ok(SpotifyAllSearchApi.spotifyArtistSearchApi(spotifyApi.getAccessToken(), keyword, offset, limit));
    }


    @ApiOperation(value = "노래상세검색API", notes = "musciId를 입력해주세요")
    @GetMapping("/track/{musicId}")
    public ResponseEntity<TrackDto> trackSearch(@PathVariable("musicId") String musicId) {
        SpotifyApi spotifyApi = spotifyApiAuthorization.clientCredentials_Sync();
        return ResponseEntity.ok(SpotifyAllSearchApi.getTrackApi(spotifyApi.getAccessToken(),musicId));
    }

    @ApiOperation(value = "앨범상세검색API",notes = "앨범속 노래들API입니다! 앨범관련된 것도(ex.앨범이미지, 앨범제목) 같이 드리면 좋았겠는데 그API랑 앨범속 노래API랑 달라서 앨범관련된건 그 전페이지에서 그대로 가져가시는게 나을 것 같습니다! 혹시 앨범관련쪽도 API쓰고 싶으시면 말씀해주세요~")
    @GetMapping("/album/{albumId}")
    public ResponseEntity<List<AlbumTrackDto>> albumSearch(@PathVariable("albumId") String albumId){
        SpotifyApi spotifyApi = spotifyApiAuthorization.clientCredentials_Sync();
        return ResponseEntity.ok(SpotifyAllSearchApi.getAlbumTrackApi(spotifyApi.getAccessToken(),albumId));
    }



}
