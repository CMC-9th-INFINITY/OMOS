package com.infinity.omos.service;

import com.infinity.omos.api.SpotifyAllSearchApi;
import com.infinity.omos.api.SpotifyApiAuthorization;
import com.infinity.omos.domain.*;
import com.infinity.omos.domain.Posts.Posts;
import com.infinity.omos.domain.Posts.PostsRepository;
import com.infinity.omos.dto.*;
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
    private final MusicRepository musicRepository;


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
                    getPostsDetailResponseDto(post, user, trackDto)
            );
        }
        return postsDetailResponseDtos;
    }

    @Transactional
    public HashMap<String, Long> save(PostsRequestDto requestDto) {
        if (!musicRepository.existsById(requestDto.getMusicId())) {
            musicRepository.save(Music.builder().id(requestDto.getMusicId()).build());
        }

        HashMap<String, Long> postId = new HashMap<>();
        Posts posts = requestDto.toPosts(musicRepository.getById(requestDto.getMusicId()), userRepository.getById(requestDto.getUserId()));
        postId.put("postId", postsRepository.save(posts).getId());
        return postId;
    }

    @Transactional
    public StateDto plusViewsCnt(Long postsId) {
        Posts posts = postsRepository.findById(postsId).orElseThrow(() -> new RuntimeException("해당 레코드는 존재하지 않는 레코드입니다"));
        posts.updateCnt();
        return StateDto.builder().state(true).build();
    }

    @Transactional(readOnly = true)
    public List<MyRecordDto> selectMyPosts(Long userId) {
        SpotifyApi spotifyApi = spotifyApiAuthorization.clientCredentials_Sync();

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다."));
        List<Posts> posts = queryRepository.findPostsByUserId(user);

        List<MyRecordDto> myRecordDtos = new ArrayList<>();
        for (Posts post : posts) {
            TrackDto trackDto = SpotifyAllSearchApi.getTrackApi(spotifyApi.getAccessToken(), post.getMusicId().getId());
            myRecordDtos.add(
                    MyRecordDto.builder()
                            .music(getMusicDto(trackDto))
                            .recordTitle(post.getTitle())
                            .recordContents(post.getContents())
                            .recordId(post.getId())
                            .createdDate(post.getCreatedDate())
                            .category(post.getCategory())
                            .isPublic(post.getIsPublic())
                            .build()

            );

        }
        return myRecordDtos;
    }

    MusicDto getMusicDto(TrackDto trackDto) {
        return MusicDto.builder()
                .musicId(trackDto.getMusicId())
                .musicTitle(trackDto.getMusicTitle())
                .albumImageUrl(trackDto.getAlbumImageUrl())
                .artists(trackDto.getArtists())
                .albumTitle(trackDto.getAlbumTitle())
                .build();
    }

    PostsDetailResponseDto getPostsDetailResponseDto(Posts posts, User user, TrackDto trackDto) {
        return PostsDetailResponseDto.builder()
                .createdDate(posts.getCreatedDate())
                .recordTitle(posts.getTitle())
                .recordContents(posts.getContents())
                .recordId(posts.getId())
                .viewsCnt(posts.getCnt())
                .userId(posts.getUserId().getId())
                .nickname(posts.getUserId().getNickname())
                .isLiked(queryRepository.existsLikeByUserIdPostId(user,posts))
                .isScraped(queryRepository.existsScarpByUserIdPostId(user,posts))
                .likeCnt(likeRepository.countByPostId(posts))
                .scrapCnt(scrapRepository.countByPostId(posts))
                .music(getMusicDto(trackDto))
                .category(posts.getCategory())
                .build();
    }

    @Transactional(readOnly = true)
    public PostsDetailResponseDto selectMyPost(Long postsId, Long userId) {
        SpotifyApi spotifyApi = spotifyApiAuthorization.clientCredentials_Sync();

        Posts posts = postsRepository.findById(postsId).orElseThrow(() -> new RuntimeException("해당 레코드는 존재하지 않는 레코드입니다"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));
        TrackDto trackDto = SpotifyAllSearchApi.getTrackApi(spotifyApi.getAccessToken(), posts.getMusicId().getId());
        //User user = userRepository.getById(userId);


        PostsDetailResponseDto postsDetailResponseDto = getPostsDetailResponseDto(posts, user, trackDto);
        postsDetailResponseDto.setIsPublic(posts.getIsPublic());
        return postsDetailResponseDto;
    }

    @Transactional
    public StateDto setPublic(Long postsId) {
        Posts posts = postsRepository.findById(postsId).orElseThrow(() -> new RuntimeException("해당 레코드는 존재하지 않는 레코드입니다"));
        posts.updatePublic();
        return StateDto.builder().state(true).build();
    }

    @Transactional
    public HashMap<String, Long> update(Long postsId, PostsUpdateDto postsUpdateDto) {
        Posts posts = postsRepository.findById(postsId).orElseThrow(() -> new RuntimeException("해당 레코드는 존재하지 않는 레코드입니다"));
        posts.updatePosts(postsUpdateDto.getTitle(), postsUpdateDto.getContents());

        HashMap<String, Long> postId = new HashMap<>();
        postId.put("postId", postsRepository.save(posts).getId());
        return postId;
    }

    @Transactional
    public StateDto delete(Long postsId) {
        Posts posts = postsRepository.findById(postsId).orElseThrow(() -> new RuntimeException("해당 레코드는 존재하지 않는 레코드입니다"));
        postsRepository.delete(posts);
        return StateDto.builder().state(true).build();
    }


}
