package com.infinity.omos.controller;

import com.infinity.omos.dto.StateDto;
import com.infinity.omos.service.ScrapService;
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
@RequestMapping("/api/scrap")
@RequiredArgsConstructor
@Api(tags = {"스크랩관련 API"})
public class ScrapController {
    private final ScrapService scrapService;

    @ApiOperation(value = "스크랩 누르기", notes = "1.해당 레코드는 존재하지 않는 레코드입니다\n2.해당 유저는 존재하지 않는 유저입니다\n3.이미 스크랩 되어있습니다 의 오류가 존재합니다")
    @PostMapping("/save/{postsId}/{userId}")
    public ResponseEntity<StateDto> saveLike(@PathVariable("postsId") Long postId, @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(scrapService.save(postId, userId));
    }

    @ApiOperation(value = "좋아요 취소", notes = "1.해당 레코드는 존재하지 않는 레코드입니다\n2.해당 유저는 존재하지 않는 유저입니다 의 오류가 존재합니다")
    @DeleteMapping("/delete/{postsId}/{userId}")
    public ResponseEntity<StateDto> deleteLike(@PathVariable("postsId") Long postId, @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(scrapService.delete(postId, userId));
    }
}
