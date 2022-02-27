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
public class PostsRequestDto {
    private String musicId;

    private Long userId;
    private Category category;


}
