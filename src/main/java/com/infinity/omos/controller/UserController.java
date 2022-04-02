package com.infinity.omos.controller;

import com.infinity.omos.dto.*;
import com.infinity.omos.service.AuthService;
import com.infinity.omos.service.PostsService;
import com.infinity.omos.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Api(tags = {"유저관련 API"})
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final PostsService postsService;


    @ApiOperation(value = "내 프로필 불러오기 API",notes = "지금은 내 프로필 불러오기로 한건데.. 나중에 다른 사람 프로필도 불러올 게 생긴다면 이거 쓰시면 될것같아요!")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> selectUserProfile(@PathVariable Long userId){
        return ResponseEntity.ok(userService.selectUserProfile(userId));
    }

    @ApiOperation(value = "내 프로필 수정 API")
    @PutMapping("/update/profile")
    public ResponseEntity<StateDto> updateUserProfile(@RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.ok(userService.updateUserProfile(userRequestDto));
    }

    @ApiOperation(value = "비밀번호 변경 API")
    @PutMapping("/update/password")
    public ResponseEntity<StateDto> updatePassword(@RequestBody PasswordDto passwordDto){
        return ResponseEntity.ok(userService.updatePassword(passwordDto));
    }

    @ApiOperation(value = "유저 신고API",notes = "현재는 한번만 신고당해도 바아로 삭제")
    @PutMapping("/{userId}/report")
    public ResponseEntity<StateDto> reportUser(@PathVariable Long userId){
        List<MyRecordDto> myRecordDtoList = postsService.selectMyPosts(userId);
        myRecordDtoList.stream().map(MyRecordDto::getRecordId).collect(Collectors.toList()).forEach(postsService::delete);
        return ResponseEntity.ok(authService.signOut(userId));
    }







}
