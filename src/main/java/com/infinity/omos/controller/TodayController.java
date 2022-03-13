package com.infinity.omos.controller;


import com.infinity.omos.dto.DjDto;
import com.infinity.omos.dto.MusicDto;
import com.infinity.omos.dto.PostsResponseDto;
import com.infinity.omos.dto.TrackDto;
import com.infinity.omos.service.TodayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/today")
@RequiredArgsConstructor
@Api(tags = {"투데이 API"})
public class TodayController {
    private final TodayService todayService;

    @ApiOperation(value = "오늘의 노래")
    @GetMapping("/music-of-today")
    public ResponseEntity<MusicDto> musicOfToday(){
        return ResponseEntity.ok(todayService.musicOfToday());
    }

    @ApiOperation(value = "지금 인기있는 레코드",notes = "인기 있는 레코드 3개로 알고있어서 3개의 게시글이 반환됩니다.")
    @GetMapping("/famous-records-of-today")
    public ResponseEntity<List<PostsResponseDto>> famousRecordsOfToday(){
        return ResponseEntity.ok(todayService.famousRecordsOfToday());
    }

    @ApiOperation(value = "OMOS 추천 DJ")
    @GetMapping("/recommend-dj")
    public ResponseEntity<List<DjDto>> recommendedDjOnToday(){
        return ResponseEntity.ok(todayService.recommendedDjOnToday());
    }

    @ApiOperation(value = "내가 사랑했던 노래")
    @GetMapping("/music-loved")
    public ResponseEntity<Object> musicILoved(){
        return ResponseEntity.ok(todayService.randomPostOnToday());
    }
}
