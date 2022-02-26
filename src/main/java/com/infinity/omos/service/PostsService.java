package com.infinity.omos.service;

import com.infinity.omos.api.SpotifyAllSearchApi;
import com.infinity.omos.api.SpotifyApiAuthorization;
import com.infinity.omos.domain.Category;
import com.infinity.omos.domain.Posts;
import com.infinity.omos.domain.PostsRepository;
import com.infinity.omos.domain.QueryRepository;
import com.infinity.omos.dto.AlbumsDto;
import com.infinity.omos.dto.PostsMatchingCategoryDto;
import com.infinity.omos.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
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
    private final SpotifyApiAuthorization spotifyApiAuthorization;

    @Transactional(readOnly = true)
    public HashMap<Category,List<PostsResponseDto>> selectRecordsMatchingAllCategory() {
        HashMap<Category,List<PostsResponseDto>> postsMatchingCategoryDtos = new HashMap<>();
        for (Category category : Category.values()) {
            postsMatchingCategoryDtos.put(category,selectRecordsMatchingCategory(category, 5));
        }

        return postsMatchingCategoryDtos;
    }

    @Transactional(readOnly = true)
    public List<PostsResponseDto> selectRecordsMatchingCategory(Category category, int size) {
        SpotifyApi spotifyApi = spotifyApiAuthorization.clientCredentials_Sync();

        List<Posts> posts = queryRepository.findPostsByCategory(category, size);
        List<PostsResponseDto> postsResponseDtos = new ArrayList<>();
        for (Posts post : posts) {
            AlbumsDto albumsDto = SpotifyAllSearchApi.getTrackApi(spotifyApi.getAccessToken(), post.getMusicId().getId());
            postsResponseDtos.add(
                    PostsResponseDto.builder()
                            .albumTitle(albumsDto.getAlbumTitle())
                            .artists(albumsDto.getArtists())
                            .musicId(albumsDto.getMusicId())
                            .musicTitle(albumsDto.getMusicTitle())
                            .recordId(post.getId())
                            .recordTitle(post.getTitle())
                            .nickname(post.getUserId().getNickname())
                            .userId(post.getUserId().getId())
                            .build()
            );

        }

        return postsResponseDtos;
    }

}
