package com.infinity.omos.controller;

import com.infinity.omos.dto.*;
import com.infinity.omos.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<StateDto> signup(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(authService.signUp(signUpDto));
    }

    @ApiOperation(value = "재발급", notes = "refreshtoken을 보내주세요")
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenDto tokenDto) {
        return ResponseEntity.ok(authService.reissue(tokenDto));
    }

    @ApiOperation(value = "SNS회원가입", notes = "kakao의 id 혹은 apple의 email을 넣어주세요. id 또한 eamil에 넣어주시면 됩니다. type은 KAKAO, APPLE 에서 골라주세요. \n 회원가입이 완료될경우, 자동으로 로그인하여 Token을 주는 식으로 생각했습니다")
    @PostMapping("/sns-signup")
    public ResponseEntity<TokenDto> kakaoLogin(@RequestBody SnsSignUpDto snsSignUpDto) {
        return ResponseEntity.ok(authService.snsSignUp(snsSignUpDto));
    }

    @ApiOperation(value = "이메일중복체크", notes = "중복이면 false, 중복이 아니면 true를 보내드려요")
    @GetMapping("/check-email")
    public ResponseEntity<StateDto> checkDuplicatedEmail(@RequestParam(name = "email") String email) {
        return ResponseEntity.ok(authService.checkDuplicatedEmail(email));
    }

    @ApiOperation(value = "SNS로그인",notes = "kakao의 id 혹은 apple의 email을 넣어주세요. kakao id 또한 eamil에 넣어주시면 됩니다. type은 KAKAO, APPLE 에서 골라주세요")
    @PostMapping("/sns-login")
    public ResponseEntity<TokenDto> snsLogin(@RequestBody SnsLoginDto snsLoginDto){
        return ResponseEntity.ok(authService.snsLogin(snsLoginDto));
    }

}