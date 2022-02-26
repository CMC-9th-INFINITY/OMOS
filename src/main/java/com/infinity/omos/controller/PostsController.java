package com.infinity.omos.controller;

import com.infinity.omos.domain.Category;
import com.infinity.omos.dto.PostsResponseDto;
import com.infinity.omos.service.PostsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api/all-records")
@RequiredArgsConstructor
@Api(tags = {"전체레코드"})
public class PostsController {
    private final PostsService postsService;

    @ApiOperation(value = "전체레코드 처음페이지", notes = "전체레코드에서 각 카테고리별로 가져가시면 될것같아요\n artist가 list인 이유는 가수가 여러명일 때도 있어서 spotify에서 그렇게 해놓은 것 같아서 저도 그대로 했습니다!")
    @GetMapping("/select-all")
    public ResponseEntity<HashMap<Category,List<PostsResponseDto>>> selectPostsMatchingCategory() {
        return ResponseEntity.ok(postsService.selectRecordsMatchingAllCategory());
    }
}
