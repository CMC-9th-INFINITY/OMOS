package com.infinity.omos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {
    private Long userId;
    private String profileUrl;
    private String nickname;

}
