package com.infinity.omos.service;

import com.infinity.omos.config.jwt.JwtTokenProvider;
import com.infinity.omos.domain.*;
import com.infinity.omos.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final QueryRepository queryRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional(readOnly = true)
    public StateDto checkDuplicatedEmail(String email) {
        return StateDto.builder()
                .state(!userRepository.existsByEmail(email)).build();
    }

    @Transactional
    public TokenDto login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));


        UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication(); // ID/PW로 AuthenticationToken 생성
        return createToken(authenticationToken);
    }

    @Transactional
    public StateDto signUp(SignUpDto signUpDto) {
        if (userRepository.existsByNickname(signUpDto.getNickname())) {
            throw new RuntimeException("이미 있는 닉네임입니다");
        }

        User user = User.toUser(signUpDto, Authority.ROLE_USER, passwordEncoder);
        userRepository.save(user);
        return StateDto.builder().state(true).build();
    }

    @Transactional
    public TokenDto reissue(TokenDto tokenDto) {

        // 2. Access Token 에서 User ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenDto.getAccessToken());

        // 1. Refresh Token 검증 -> 원래 spring batch나 redis로 주기적으로 만료된 refresh Token을 삭제해주어야하지만, 지금은 우선 유효하지 않을 경우, 저장소에서 검색후 삭제.
        if (!jwtTokenProvider.validateToken(tokenDto.getRefreshToken())) {
            refreshTokenRepository.deleteById(authentication.getName());
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 3. 저장소에서 User ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByUserEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getToken().equals(tokenDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto newTokenDto = jwtTokenProvider.createToken(authentication);
        newTokenDto.updateId(tokenDto.getUserId());

        // 6. 저장소 정보 업데이트
        refreshToken.update(newTokenDto.getRefreshToken());

        // 토큰 발급
        return newTokenDto;
    }


    @Transactional
    public TokenDto snsSignUp(SnsSignUpDto snsSignUpDto) {
        if (userRepository.existsByNickname(snsSignUpDto.getNickname())) {
            throw new RuntimeException("이미 있는 닉네임입니다");
        }
        if (snsSignUpDto.getType() == ProviderType.KAKAO && !snsSignUpDto.getEmail().contains("@kakao.com")) {
            throw new RuntimeException("카카오 아이디 형식을 확인해주세요");
        }

        User user = User.toUser(snsSignUpDto, Authority.ROLE_USER, passwordEncoder);
        userRepository.save(user);

        return snsLogin(SnsLoginDto.builder()
                .email(user.getEmail())
                .type(snsSignUpDto.getType())
                .build());
    }


    @Transactional
    public TokenDto snsLogin(SnsLoginDto snsLoginDto) {
        if (userRepository.existsByEmail(snsLoginDto.getEmail())) {
            throw new RuntimeException("해당하는 유저가 존재하지 않습니다");
        }
        UsernamePasswordAuthenticationToken authenticationToken = snsLoginDto.toAuthentication(); // ID/PW로 AuthenticationToken 생성
        return createToken(authenticationToken);
    }

    @Transactional
    public TokenDto createToken(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);// 사용자 비밀번호 체크, CustomUserDetailsService에서의 loadUserByUsername 메서드가 실행됨

        SecurityContextHolder.getContext().setAuthentication(authentication);//securityContext에 저장

        String email = authentication.getName();

        TokenDto tokenDto = jwtTokenProvider.createToken(authentication);
        RefreshToken refreshToken = RefreshToken.builder()
                .userEmail(email)
                .token(tokenDto.getRefreshToken())
                .build();

        tokenDto.updateId(queryRepository.findUserIdByUserEmail(email));
        refreshTokenRepository.save(refreshToken);
        return tokenDto;
    }

    @Transactional
    public StateDto logout(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));
        RefreshToken refreshToken = refreshTokenRepository.findByUserEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));
        refreshTokenRepository.delete(refreshToken);
        return StateDto.builder().state(true).build();
    }

    @Transactional
    public void signOut(Long userId){

    }


}