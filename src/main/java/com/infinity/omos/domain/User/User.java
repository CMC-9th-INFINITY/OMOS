package com.infinity.omos.domain.User;

import com.infinity.omos.domain.Authority;
import com.infinity.omos.domain.BaseTimeEntity;
import com.infinity.omos.dto.DjDto;
import com.infinity.omos.dto.SignUpDto;
import com.infinity.omos.dto.SnsSignUpDto;
import com.infinity.omos.dto.UserRequestDto;
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
public class User extends BaseTimeEntity {

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
         return User.builder()
                    .email(snsSignUpDto.getEmail())
                    .password(passwordEncoder.encode(snsSignUpDto.getEmail()))
                    .nickname(snsSignUpDto.getNickname())
                    .authority(authority)
                    .build();

    }

    public DjDto toMyDjDto(){
        return DjDto.builder()
                .userId(id)
                .nickname(nickname)
                .profileUrl(profileUrl)
                .build();
    }

    public void updateProfile(UserRequestDto userRequestDto){
        this.profileUrl= userRequestDto.getProfileUrl();
        this.nickname = userRequestDto.getNickname();

    }

    public void updatePassword(String password , PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }
}

