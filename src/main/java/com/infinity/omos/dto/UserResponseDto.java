package com.infinity.omos.dto;

import com.infinity.omos.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long userId;

    public static UserResponseDto of(User user) {
        return new UserResponseDto(user.getId());
    }

}