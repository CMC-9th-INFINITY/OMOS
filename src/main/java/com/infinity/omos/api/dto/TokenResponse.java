package com.infinity.omos.api.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@Setter
public class TokenResponse {

    private String accessToken;
    private String tokenType;
    private Long expiresIn;
}
