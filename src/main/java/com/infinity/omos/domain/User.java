package com.infinity.omos.domain;

import com.infinity.omos.dto.SnsSignUpDto;
import com.infinity.omos.dto.SignUpDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@Entity
@Builder
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(nullable = false)
    private String nickname;

    private String profileUrl;


    @Enumerated(EnumType.STRING)
    private Authority authority;

    public static User toUser(SignUpDto signUpDto, Authority authority, PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .nickname(signUpDto.getNickname())
                .authority(authority)
                .build();
    }

    public static User toUser(SnsSignUpDto snsSignUpDto, Authority authority, PasswordEncoder passwordEncoder) {
        if (snsSignUpDto.getType() == ProviderType.KAKAO) {
            return User.builder()
                    .email(snsSignUpDto.getEmail() + "@kakao.com")
                    .password(passwordEncoder.encode(snsSignUpDto.getEmail()))
                    .nickname(snsSignUpDto.getNickname())
                    .authority(authority)
                    .build();
        } else if (snsSignUpDto.getType() == ProviderType.APPLE) {
            return User.builder()
                    .email(snsSignUpDto.getEmail())
                    .password(passwordEncoder.encode(snsSignUpDto.getEmail()))
                    .nickname(snsSignUpDto.getNickname())
                    .authority(authority)
                    .build();
        }else {
            throw new RuntimeException("type을 다시 확인해주세요");
        }

    }
}

