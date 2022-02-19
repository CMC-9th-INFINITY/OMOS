package com.infinity.omos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.infinity.omos.domain.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class SnsSignUpDto {

    @NotNull
    private String email;

    @NotNull
    private String nickname;

    @NotNull
    private ProviderType type;


}
