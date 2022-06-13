package com.infinity.omos.controller;

import com.infinity.omos.domain.ReportType;
import com.infinity.omos.dto.ReportDto;
import com.infinity.omos.dto.StateDto;
import com.infinity.omos.dto.UserRequestDto;
import com.infinity.omos.service.BlockService;
import com.infinity.omos.service.FollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/block")
@RequiredArgsConstructor
@Api(tags = {"차단 관련 API"})
public class BlockController {

    private final BlockService blockService;
    private final FollowService followService;

    @ApiOperation(value = "차단하기", notes = "차단에서 reportReason은 쓰지말아주세요~ 아마 써도 아무일없을것같긴하지망...fromUserId는 서비스를 사용하고 있는 유저, 유저차단이면 type은 User, toUserId에 차단할 유저를 적어주시고 게시글차단이면 type은 Record, recordId에 게시글Id를 넣어주세요! type이 user인데, postId가 있거나 type이 record인데, touserId가 없게 해주세요! 근데 뭐 있어도 인식안되게 해놓긴했지만! 에러는 모르는 거니..")
    @PostMapping("/save/{type}")
    public ResponseEntity<StateDto> save(@PathVariable ReportType type, @RequestBody ReportDto reportDto) {
        if (type == ReportType.User) {
            followService.delete(reportDto.getFromUserId(), reportDto.getToUserId());
        }
        return ResponseEntity.ok(blockService.save(type, reportDto, null));
    }

    @ApiOperation(value = "차단취소", notes = "")
    @DeleteMapping("/delete/{fromUserId}/{toUserId}")
    public ResponseEntity<StateDto> delete(@PathVariable Long fromUserId, @PathVariable Long toUserId) {
        return ResponseEntity.ok(blockService.delete(fromUserId, toUserId));
    }

    @ApiOperation(value = "해당 유저의 다른 유저 차단 목록")
    @GetMapping("/select/{userId}")
    public ResponseEntity<List<UserRequestDto>> getBlockList(@PathVariable Long userId) {
        return ResponseEntity.ok(blockService.getByUserId(userId));
    }


}
