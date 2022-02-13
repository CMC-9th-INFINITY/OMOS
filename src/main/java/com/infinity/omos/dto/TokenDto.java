package com.infinity.omos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private Long schoolId;

    public void updateId(Long userId, Long schoolId) {
        this.userId = userId;
        this.schoolId = schoolId;
    }

}
