package com.infinity.omos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class KakaoSignUpDto {

    @NotNull
    private String id;

    @NotNull
    private String nickname;

    public SignUpDto to(String id, String nickname){
        return SignUpDto.builder()
                .email(id)
                .password(id)
                .nickname(nickname)
                .build();
    }
}
