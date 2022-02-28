package com.infinity.omos.controller;

import com.infinity.omos.dto.StateDto;
import com.infinity.omos.service.LikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/like")
@RequiredArgsConstructor
@Api(tags = {"좋아요관련 API"})
public class LikeController {
    private final LikeService likeService;

    @ApiOperation(value = "좋아요 누르기", notes = "1.해당 레코드는 존재하지 않는 레코드입니다\n2.해당 유저는 존재하지 않는 유저입니다\n3.이미 좋아요가 눌러져있습니다 의 오류가 존재합니다")
    @PostMapping("/save/{postsId}/{userId}")
    public ResponseEntity<StateDto> saveLike(@PathVariable("postsId") Long postId, @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(likeService.save(postId, userId));
    }

    @ApiOperation(value = "좋아요 취소", notes = "1.해당 레코드는 존재하지 않는 레코드입니다\n2.해당 유저는 존재하지 않는 유저입니다 의 오류가 존재합니다")
    @DeleteMapping("/delete/{postsId}/{userId}")
    public ResponseEntity<StateDto> deleteLike(@PathVariable("postsId") Long postId, @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(likeService.delete(postId, userId));
    }
}
