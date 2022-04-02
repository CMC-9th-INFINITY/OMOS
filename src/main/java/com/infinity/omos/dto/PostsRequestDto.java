package com.infinity.omos.dto;

import com.infinity.omos.domain.Category;
import com.infinity.omos.domain.Music.Music;
import com.infinity.omos.domain.Posts.Posts;
import com.infinity.omos.domain.User.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class PostsRequestDto {
    private String musicId;
    private Long userId;
    private Category category;
    private Boolean isPublic;
    private String recordTitle;
    private String recordContents;
    private String recordImageUrl;

    public Posts toPosts(Music music,User user) {
        return Posts.builder()
                .musicId(music)
                .userId(user)
                .category(category)
                .isPublic(isPublic)
                .title(recordTitle)
                .imageUrl(recordImageUrl)
                .contents(recordContents)
                .cnt(0)
                .build();
    }
}
