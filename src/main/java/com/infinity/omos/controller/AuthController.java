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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Api(tags = {"회원가입 API"})
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "로그인",notes = "이메일 로그인입니다")
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @ApiOperation(value = "이메일회원가입",notes = "이메일 회원가입입니다")
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(authService.signUp(signUpDto));
    }

    @ApiOperation(value = "재발급",notes = "refreshkey를 보내주세요")
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenDto tokenDto) {
        return ResponseEntity.ok(authService.reissue(tokenDto));
    }

}