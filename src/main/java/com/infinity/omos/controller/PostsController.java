package com.infinity.omos.controller;

import com.infinity.omos.domain.Category;
import com.infinity.omos.dto.*;
import com.infinity.omos.service.PostsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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

    @ApiOperation(value = "전체레코드에서 상세보기", notes = "postId는 처음엔 아무것도 안주시면 됩니다. 그리고 두번째부터는 첫번째에 받았던 마지막 postId를 넣어주시면 그 이후 post부터 나오게 됩니다.")
    @GetMapping("/select/category/{category}")
    public ResponseEntity<List<PostsDetailResponseDto>> selectPostsMatchingCategory(@PathVariable("category") Category category, @RequestParam("sortType") PostsService.SortType sortType, Long postId, @RequestParam("size") int pageSize, @RequestParam("userid") Long userId) {
        return ResponseEntity.ok(postsService.selectRecordsByCategory(category, sortType, postId, pageSize, userId));
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

    @ApiOperation(value = "MY레코드 전체 불러오기", notes = "페이징 처리 안하고 다 불러오는 걸로 했습니다!")
    @GetMapping("/{userId}")
    public ResponseEntity<List<MyRecordDto>> selectMyPosts(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(postsService.selectMyPosts(userId));
    }

    @ApiOperation(value = "My레코드 전체 불러오기(취소)", notes = "페이징 처리 안하고 다 불러오는 걸로 했습니다!")
    @GetMapping("/{userId}/cancel")
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

    @ApiOperation(value = "한 노래에 따른 레코드 API", notes = "해당 노래를 클릭하면 해당하는 레코드들이 나오게 되는 부분입니다! 이 부분은 다른 페이징이랑 달라서 죄송해요ㅠㅠ 제가 여러가지 시도하다보니ㅠㅠ 아마 그 전 페이징도 나중에 바뀌지 않을까 싶습니다ㅠㅠ 파라미터에 대해 설명 드리자면, postId는 처음엔 아무것도 안주시면 됩니다. 그리고 두번째부터는 첫번째에 받았던 마지막 postId를 넣어주시면 그 이후 post부터 나오게 됩니다.")
    @GetMapping("/select/music/{musicId}")
    public ResponseEntity<List<PostsDetailResponseDto>> selectPostsByMusicId(@PathVariable("musicId") String musicId, @RequestParam("sortType") PostsService.SortType sortType, Long postId, @RequestParam("size") int pageSize, @RequestParam("userId") Long userId) {
        return ResponseEntity.ok(postsService.selectPostsByMusicId(musicId, sortType, postId, pageSize, userId));
    }

    @ApiOperation(value = "해당 유저 레코드 목록 불러오기", notes = "fromUserId는 지금 이용하고 있는 유저, toUserId는 레코드 목록을 불러오고 싶은 유저")
    @GetMapping("/select/user/{fromUserId}/{toUserId}")
    public ResponseEntity<List<PostsDetailResponseDto>> selectPostsByUserId(@PathVariable("fromUserId") Long fromUserId, @PathVariable("toUserId") Long toUserId) {
        return ResponseEntity.ok(postsService.selectPostsByUserId(fromUserId, toUserId));
    }

    @ApiOperation(value = "MyDj 전체 레코드 목록 불러오기", notes = "userId는 지금 이용하고 있는 유저")
    @GetMapping("/select/{userId}/my-dj")
    public ResponseEntity<List<PostsDetailResponseDto>> selectMyDj(@PathVariable("userId") Long userId, Long postId, @RequestParam("size") int pageSize) {
        return ResponseEntity.ok(postsService.selectMyDjPosts(userId, postId, pageSize));
    }

    @ApiOperation(value = "레코드 하나 가져오기", notes = "userId는 지금 이용하고 있는 유저")
    @GetMapping("/select/{postId}/user/{userId}")
    public ResponseEntity<PostsDetailResponseDto> selectPost(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postsService.selectPostById(postId, userId));
    }
}
