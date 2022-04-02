package com.infinity.omos.controller;

import com.infinity.omos.domain.ReportType;
import com.infinity.omos.dto.ReportDto;
import com.infinity.omos.dto.StateDto;
import com.infinity.omos.service.BlockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/block")
@RequiredArgsConstructor
@Api(tags = {"차단 관련 API"})
public class BlockController {

    private final BlockService blockService;

    @ApiOperation(value = "차단하기", notes = "fromUserId는 서비스를 사용하고 있는 유저, 유저차단이면 type은 User, toUserId에 차단할 유저를 적어주시고 게시글차단이면 type은 Record, recordId에 게시글Id를 넣어주세요! type이 user인데, postId가 있거나 type이 record인데, touserId가 없게 해주세요! 근데 뭐 있어도 인식안되게 해놓긴했지만! 에러는 모르는 거니..")
    @PostMapping("/save/{type}")
    public ResponseEntity<StateDto> save(@PathVariable ReportType type, @RequestBody ReportDto reportDto){
        return ResponseEntity.ok(blockService.save(type,reportDto,null));
    }


}
