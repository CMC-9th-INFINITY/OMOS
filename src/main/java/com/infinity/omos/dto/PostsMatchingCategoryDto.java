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
public class PostsMatchingCategoryDto {
    private Category category;
    private List<PostsResponseDto> postsResponseDtos;

}
