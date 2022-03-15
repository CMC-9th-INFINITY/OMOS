package com.infinity.omos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class PostsUpdateDto {
    private String title;
    private String contents;
    private Boolean isPublic;
    private String recordImageUrl;
}
