package com.infinity.omos.dto;

import com.infinity.omos.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class AllPostsResponseDto {

    private Long musicId;
    private String singTitle;
    private String artist;
    private String albumName;
    private String recordTitle;
    private String recordWriter;
    private Long recordId;




}
