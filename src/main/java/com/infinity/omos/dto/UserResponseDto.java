package com.infinity.omos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springfox.documentation.spring.web.json.Json;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {
    private Long userId;
    private String profileUrl;
    private String nickname;
    private Boolean isFollowed;

    public UserResponseDto updateIsFollowed(Boolean isFollowed){
        this.isFollowed = isFollowed;
        return this;
    }
}