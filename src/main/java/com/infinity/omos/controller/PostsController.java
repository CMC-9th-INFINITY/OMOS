package com.infinity.omos.controller;

import com.infinity.omos.domain.Category;
import com.infinity.omos.dto.*;
import com.infinity.omos.service.PostsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api/records")
@RequiredArgsConstructor
@Api(tags = {"레코드관련 API"})
public class PostsController {
    private final PostsService postsService;

    @ApiOperation(value = "전체레코드 처음페이지", notes = "전체레코드에서 각 카테고리별로 가져가시면 될것같아요\n artist가 list인 이유는 가수가 여러명일 때도 있어서 spotify에서 그렇게 해놓은 것 같아서 저도 그대로 했습니다!")
    @GetMapping("/select")
    public ResponseEntity<HashMap<Category, List<PostsResponseDto>>> selectAllPosts() {
        return ResponseEntity.ok(postsService.selectRecordsMatchingAllCategory());
    }

    @ApiOperation(value = "전체레코드에서 상세보기", notes = "예를 들어 게시판이 있다고 생각하고 한 페이지마다 3개씩 글이 있고 3번째 페이지에 있는 글을 가져오고 싶다! 하면 page=3&size=3 이런식으로 하시면 됩니당! 이건 글을 불러올때 한번에 다 불러오는건 낭비니까 계속 조금씩 불러오는걸루 생각하시면 되고 실전에서 쓰실때는 size는 고정으로 page는 0부터 차근차근 높여가시면 될 듯 합니다!")
    @GetMapping("/select/category/{category}")
    public ResponseEntity<List<PostsDetailResponseDto>> selectPostsMatchingCategory(@PathVariable("category") Category category, @RequestParam("userid") Long userId, Pageable pageable) {
        return ResponseEntity.ok(postsService.selectRecordsByCategory(category, pageable, userId));
    }

    @ApiOperation(value = "레코드 저장")
    @PostMapping("/save")
    public ResponseEntity<StateDto> savePosts(@RequestBody PostsRequestDto postsRequestDto) {
        return ResponseEntity.ok(postsService.save(postsRequestDto));
    }

    @ApiOperation(value = "레코드 조회수 상승", notes = "return값으로 레코드 업데이트된 조회수 필요하거나 암튼 리턴값 필요하신거있으시면 말씀해주세요! 지금은 딱히 필요한거 있는지 몰라서 나뒀어요")
    @PutMapping("/{postId}")
    public ResponseEntity<StateDto> plusViews(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postsService.plusViewsCnt(postId));
    }

    @ApiOperation(value = "MY레코드 전체 불러오기(취소)", notes = "페이징 처리 안하고 다 불러오는 걸로 했습니다!")
    @GetMapping("/{userId}/cancel")
    public ResponseEntity<List<MyRecordDto>> selectMyPosts(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(postsService.selectMyPosts(userId));
    }

    @ApiOperation(value = "My레코드 전체 불러오기", notes = "페이징 처리 안하고 다 불러오는 걸로 했습니다!")
    @GetMapping("/{userId}")
    public ResponseEntity<List<PostsDetailResponseDto>> selectMyPost(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(postsService.selectMyPost(userId));
    }

    @ApiOperation(value = "레코드 비공개 공개 활성화", notes = "비공개였으면 공개, 공개였으면 비공개로 전환됩니다.")
    @PutMapping("/{postId}/ispulic")
    public ResponseEntity<StateDto> setPublic(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postsService.setPublic(postId));
    }

    @ApiOperation(value = "레코드 수정", notes = "제목,내용 둘 다 보내주세요")
    @PutMapping("/update/{postId}")
    public ResponseEntity<HashMap<String, Long>> updatePosts(@PathVariable("postId") Long postId, @RequestBody PostsUpdateDto postsUpdateDto) {
        return ResponseEntity.ok(postsService.update(postId, postsUpdateDto));
    }

    @ApiOperation(value = "레코드 삭제")
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<StateDto> deletePosts(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postsService.delete(postId));
    }


}
