package com.infinity.omos.controller;

import com.infinity.omos.dto.*;
import com.infinity.omos.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Api(tags = {"계정API"})
public class  AuthController {
    private final AuthService authService;
    private final PostsService postsService;
    private final EmailService emailService;
    private final S3Service s3Service;


    @ApiOperation(value = "로그인", notes = "이메일 로그인입니다")
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @ApiOperation(value = "이메일회원가입", notes = "이메일 회원가입입니다")
    @PostMapping("/signup")
    public ResponseEntity<StateDto> signup(@Valid @RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(authService.signUp(signUpDto));
    }

    @ApiOperation(value = "재발급", notes = "refreshtoken을 보내주세요")
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@Valid @RequestBody TokenDto tokenDto) {
        return ResponseEntity.ok(authService.reissue(tokenDto));
    }

    @ApiOperation(value = "SNS회원가입", notes = "kakao의 id 혹은 apple의 email을 넣어주세요. id 또한 eamil에 넣어주시면 됩니다. type은 KAKAO, APPLE 에서 골라주세요. \n 회원가입이 완료될경우, 자동으로 로그인하여 Token을 주는 식으로 생각했습니다")
    @PostMapping("/sns-signup")
    public ResponseEntity<TokenDto> kakaoLogin(@Valid @RequestBody SnsSignUpDto snsSignUpDto) {
        return ResponseEntity.ok(authService.snsSignUp(snsSignUpDto));
    }

    @ApiOperation(value = "이메일중복체크", notes = "중복이면 false, 중복이 아니면 true를 보내드려요")
    @GetMapping("/check-email")
    public ResponseEntity<StateDto> checkDuplicatedEmail(@RequestParam(name = "email") String email) {
        return ResponseEntity.ok(authService.checkDuplicatedEmail(email));
    }


    @ApiOperation(value = "SNS로그인",notes = "kakao의 id 혹은 apple의 email을 넣어주세요. kakao id 또한 eamil에 넣어주시면 됩니다. type은 KAKAO, APPLE 에서 골라주세요")
    @PostMapping("/sns-login")
    public ResponseEntity<TokenDto> snsLogin(@Valid @RequestBody SnsLoginDto snsLoginDto){
        return ResponseEntity.ok(authService.snsLogin(snsLoginDto));
    }

    @ApiOperation(value = "로그아웃",notes = "클라쪽에서 가지고 계시는 access, refresh Token 모두 삭제해주셔야합니다!! 꼭!")
    @DeleteMapping("/logout/{userId}")
    public ResponseEntity<StateDto> logout(@PathVariable Long userId){
        return ResponseEntity.ok(authService.logout(userId));
    }

    @DeleteMapping("/signout/{userId}")
    @ApiOperation(value = "계정탈퇴",notes = "현재는 계정삭제할 경우, 유저와 관련된 모든 것들이 사라지는 걸로 해두었습니다")
    public ResponseEntity<StateDto> signOut(@PathVariable Long userId){
        List<MyRecordDto> myRecordDtoList = postsService.selectMyPosts(userId);
        myRecordDtoList.stream().map(MyRecordDto::getRecordId).collect(Collectors.toList()).forEach(postsService::delete);
        s3Service.deleteFile("profile",userId.toString()+".png");
        return ResponseEntity.ok(authService.signOut(userId));
    }

    @ApiOperation(value = "이메일",notes = "코드값이 리턴됩니다.")
    @PostMapping("/email")
    public ResponseEntity<HashMap<String,String>> sendEmail(@RequestBody MailDto mailDto) throws Exception {
        return ResponseEntity.ok(emailService.sendSimpleMessage(mailDto));
    }

    @ApiOperation(value = "비밀번호 변경 API")
    @PutMapping("/update/password")
    public ResponseEntity<StateDto> updatePassword(@RequestBody PasswordDto passwordDto){
        return ResponseEntity.ok(authService.updatePassword(passwordDto));
    }

    @ApiOperation(value = "아이디로 userId가져오기")
    @GetMapping("/{email}")
    public ResponseEntity<Map<String,Long>> getUserId(@PathVariable String email){
        return ResponseEntity.ok(authService.getUserId(email));
    }


}