package com.infinity.omos.controller;

import com.infinity.omos.dto.DjprofileDto;
import com.infinity.omos.dto.DjDto;
import com.infinity.omos.dto.StateDto;
import com.infinity.omos.service.FollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/follow")
@RequiredArgsConstructor
@Api(tags = {"팔로우관련 API"})
public class FollowController {
    private final FollowService followService;

    @ApiOperation(value = "팔로우하기", notes = "해당 유저는 존재하지 않는 유저입니다\n이미 팔로우가 되어있습니다 라는 오류가 존재합니다")
    @PostMapping("/save/{fromUserId}/{toUserId}")
    public ResponseEntity<StateDto> saveFollow(@PathVariable Long fromUserId, @PathVariable Long toUserId) {
        return ResponseEntity.ok(followService.save(fromUserId, toUserId));
    }

    @ApiOperation(value = "팔로우취소", notes = "해당 유저는 존재하지 않는 유저입니다 라는 오류가 존재합니다")
    @DeleteMapping("/delete/{fromUserId}/{toUserId}")
    public ResponseEntity<StateDto> deleteFollow(@PathVariable Long fromUserId, @PathVariable Long toUserId) {
        return ResponseEntity.ok(followService.delete(fromUserId, toUserId));
    }

    @ApiOperation(value = "Dj 리스트 목록", notes = "MyDj위에 동그랗게 있는 Dj 리스트 목록입니다!")
    @GetMapping("/select/myDj/{userId}")
    public ResponseEntity<List<DjDto>> selectMyDjList(@PathVariable Long userId) {
        return ResponseEntity.ok(followService.selectMyDjList(userId));
    }

    @ApiOperation(value = "MyDj프로필", notes = "fromUserId는 지금 이 앱을 사용하는 유저이고 toUserId는 프로필을 불러올 User")
    @GetMapping("/select/{fromUserId}/{toUserId}")
    public ResponseEntity<DjprofileDto> selectFollowProfile(@PathVariable Long fromUserId, @PathVariable Long toUserId) {
        return ResponseEntity.ok(followService.selectFollowCount(fromUserId, toUserId));
    }




}
