package com.infinity.omos.service;

import com.infinity.omos.api.SpotifyAllSearchApi;
import com.infinity.omos.api.SpotifyApiAuthorization;
import com.infinity.omos.domain.*;
import com.infinity.omos.domain.Posts.Posts;
import com.infinity.omos.domain.Posts.PostsRepository;
import com.infinity.omos.dto.TrackDto;
import com.infinity.omos.dto.MusicDto;
import com.infinity.omos.dto.PostsDetailResponseDto;
import com.infinity.omos.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.michaelthelin.spotify.SpotifyApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;
    private final QueryRepository queryRepository;
    private final LikeRepository likeRepository;
    private final ScrapRepository scrapRepository;
    private final SpotifyApiAuthorization spotifyApiAuthorization;
    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public HashMap<Category, List<PostsResponseDto>> selectRecordsMatchingAllCategory() {
        HashMap<Category, List<PostsResponseDto>> postsMatchingCategoryDtos = new HashMap<>();
        for (Category category : Category.values()) {
            postsMatchingCategoryDtos.put(category, selectRecordsMatchingCategory(category, 5));
        }

        return postsMatchingCategoryDtos;
    }

    @Transactional(readOnly = true)
    public List<PostsResponseDto> selectRecordsMatchingCategory(Category category, int size) {
        SpotifyApi spotifyApi = spotifyApiAuthorization.clientCredentials_Sync();

        List<Posts> posts = queryRepository.findPostsByCategory(category, size);
        List<PostsResponseDto> postsResponseDtos = new ArrayList<>();
        for (Posts post : posts) {
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
                            .nickname(post.getUserId().getNickname())
                            .userId(post.getUserId().getId())
                            .build()
            );

        }

        return postsResponseDtos;
    }

    @Transactional(readOnly = true)
    public List<PostsDetailResponseDto> selectRecordsByCategory(Category category, Pageable pageable, Long userId) {
        SpotifyApi spotifyApi = spotifyApiAuthorization.clientCredentials_Sync();

        List<PostsDetailResponseDto> postsDetailResponseDtos = new ArrayList<>();
        Page<Posts> posts = postsRepository.findAllByCategory(category, pageable);
        for (Posts post : posts) {
            TrackDto trackDto = SpotifyAllSearchApi.getTrackApi(spotifyApi.getAccessToken(), post.getMusicId().getId());
            User user = userRepository.getById(userId);
            postsDetailResponseDtos.add(
                    PostsDetailResponseDto.builder()
                            .createdDate(post.getCreatedDate())
                            .recordTitle(post.getTitle())
                            .recordContents(post.getContents())
                            .recordId(post.getId())
                            .viewsCnt(post.getCnt())
                            .userId(userId)
                            .nickname(user.getNickname())
                            .isLiked(likeRepository.existsByUserId(user))
                            .isScraped(scrapRepository.existsByUserId(user))
                            .likeCnt(likeRepository.countByPostId(post))
                            .scrapCnt(scrapRepository.countByPostId(post))
                            .music(MusicDto.builder()
                                    .musicId(trackDto.getMusicId())
                                    .musicTitle(trackDto.getMusicTitle())
                                    .albumImageUrl(trackDto.getAlbumImageUrl())
                                    .artists(trackDto.getArtists())
                                    .albumTitle(trackDto.getAlbumTitle())
                                    .build())
                            .build()
            );
        }
        return postsDetailResponseDtos;
    }

    @Transactional
    public void save(){

    }

}
