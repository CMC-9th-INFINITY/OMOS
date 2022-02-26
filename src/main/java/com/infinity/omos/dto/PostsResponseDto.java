package com.infinity.omos.dto;

import com.infinity.omos.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class PostsResponseDto {

    private String musicId;
    private String musicTitle;
    private List<Artists> artists;
    private String albumTitle;
    private String recordTitle;
    private Long userId;
    private String nickname;
    private Long recordId;




}
