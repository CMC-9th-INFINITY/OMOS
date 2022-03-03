package com.infinity.omos.dto;

import com.infinity.omos.domain.Category;
import com.infinity.omos.domain.Music;
import com.infinity.omos.domain.Posts.Posts;
import com.infinity.omos.domain.User;
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
    private String title;
    private String contents;
    private String imageUrl;

    public Posts toPosts(Music music,User user) {
        return Posts.builder()
                .musicId(music)
                .userId(user)
                .category(category)
                .isPublic(isPublic)
                .title(title)
                .imageUrl(imageUrl)
                .contents(contents)
                .cnt(0)
                .build();
    }
}
