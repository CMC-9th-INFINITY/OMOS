package com.infinity.omos.service;

import com.infinity.omos.api.SpotifyAllSearchApi;
import com.infinity.omos.api.SpotifyApiAuthorization;
import com.infinity.omos.domain.Posts.Posts;
import com.infinity.omos.domain.Posts.PostsRepository;
import com.infinity.omos.domain.QueryRepository;
import com.infinity.omos.domain.User;
import com.infinity.omos.domain.UserRepository;
import com.infinity.omos.dto.DjDto;
import com.infinity.omos.dto.MusicDto;
import com.infinity.omos.dto.PostsResponseDto;
import com.infinity.omos.dto.TrackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.michaelthelin.spotify.SpotifyApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.infinity.omos.service.PostsService.getMusicDto;

@Service
@RequiredArgsConstructor
public class TodayService {
    private final SpotifyApiAuthorization spotifyApiAuthorization;
    private final QueryRepository queryRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public MusicDto musicOfToday() {
        SpotifyApi spotifyApi = spotifyApiAuthorization.clientCredentials_Sync();
        String musicId = queryRepository.findMusicIdOnToday();
        if (musicId == null) {
            return null;
        }
        return getMusicDto(SpotifyAllSearchApi.getTrackApi(spotifyApi.getAccessToken(), musicId));
    }

    @Transactional(readOnly = true)
    public List<PostsResponseDto> famousRecordsOfToday() {
        SpotifyApi spotifyApi = spotifyApiAuthorization.clientCredentials_Sync();
        List<Posts> postsList = queryRepository.findPostsOnToday();
        List<PostsResponseDto> postsResponseDtos = new ArrayList<>(); //이부분 지금postservice에 한 부분이랑 겹침 그래서 나중에 고도화때 따로빼서 사용하던가 암튼 그러는게 좋을것같음 뭔가 따로 이렇게 dto에 정보 조합하는 클래스를 따로 만들어도 될것같음~
        for (Posts post : postsList) {
            TrackDto trackDto = SpotifyAllSearchApi.getTrackApi(spotifyApi.getAccessToken(), post.getMusicId().getId());

            postsResponseDtos.add(
                    PostsResponseDto.builder()
                            .music(MusicDto.builder()
                                    .musicId(trackDto.getMusicId())
                                    .musicTitle(trackDto.getMusicTitle())
                                    .albumImageUrl(trackDto.getAlbumImageUrl())
                                    .artists(trackDto.getArtists())
                                    .albumTitle(trackDto.getAlbumTitle())
                                    .build()
                            )
                            .recordId(post.getId())
                            .recordTitle(post.getTitle())
                            .recordImageUrl(post.getImageUrl())
                            .nickname(post.getUserId().getNickname())
                            .userId(post.getUserId().getId())
                            .build()
            );

        }
        return postsResponseDtos;
    }

    @Transactional(readOnly = true)
    public List<DjDto> recommendedDjOnToday() {
        List<DjDto> djDtoList = new ArrayList<>();
        for (Long userId : queryRepository.findDjOnToday()) { //이부분 null나오면 for문안돌고 넘어갈지 모르겟네
            User user = userRepository.getById(userId);
            djDtoList.add(
                    DjDto.builder()
                            .userId(userId)
                            .nickName(user.getNickname())
                            .profileUrl(user.getProfileUrl())
                            .build()
            );
        }
        return djDtoList;
    }

    @Transactional(readOnly = true)
    public Object randomPostOnToday(Long userId) {
        Posts post = queryRepository.findPostByRandom(userId);

        SpotifyApi spotifyApi = spotifyApiAuthorization.clientCredentials_Sync();
        MusicDto musicDto = getMusicDto(SpotifyAllSearchApi.getTrackApi(spotifyApi.getAccessToken(), post.getMusicId().getId()));

        HashMap<String, Object> record = new HashMap<>();
        record.put("recordImageUrl", post.getImageUrl());
        record.put("recordId", post.getId());
        record.put("music", musicDto);

        return record;
    }


}
