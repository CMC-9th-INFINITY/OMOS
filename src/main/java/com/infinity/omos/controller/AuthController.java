package com.infinity.omos.controller;

import com.infinity.omos.dto.LoginDto;
import com.infinity.omos.dto.SignUpDto;
import com.infinity.omos.dto.TokenDto;
import com.infinity.omos.dto.UserResponseDto;
import com.infinity.omos.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Api(tags = {"회원가입 관련 API"})
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "로그인", notes = "이메일 로그인입니다")
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @ApiOperation(value = "이메일회원가입", notes = "이메일 회원가입입니다")
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(authService.signUp(signUpDto));
    }

    @ApiOperation(value = "재발급", notes = "refreshtoken을 보내주세요")
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenDto tokenDto) {
        return ResponseEntity.ok(authService.reissue(tokenDto));
    }

    @ApiOperation(value = "kakao로그인", notes = "kakaoaccesstoken을 보내주세요")
    @PostMapping("/kakao")
    public ResponseEntity<TokenDto> kakaoLogin(@RequestParam(name = "accesstoken") String kakaoAccessToken) throws IOException {
        return ResponseEntity.ok(authService.kakaoLogin(kakaoAccessToken));
    }

    @ApiOperation(value = "이메일중복체크", notes = "중복이면 false, 중복이 아니면 true를 보내드려요")
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkDuplicatedEmail(@RequestParam(name = "email") String email) {
        return ResponseEntity.ok(authService.checkDuplicatedEmail(email));
    }


}