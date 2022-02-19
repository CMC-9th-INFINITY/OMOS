package com.infinity.omos.dto;

import com.infinity.omos.domain.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class SnsLoginDto {

    @NotNull
    private String email;

    @NotNull
    private ProviderType type;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        if (type == ProviderType.KAKAO) {
            return new UsernamePasswordAuthenticationToken(email+ "@kakao.com", email);
        } else if (type == ProviderType.APPLE) {
            return new UsernamePasswordAuthenticationToken(email, email);
        }
        return null;
    }

}
