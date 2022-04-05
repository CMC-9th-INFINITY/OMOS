package com.infinity.omos.controller;

import com.infinity.omos.dto.StateDto;
import com.infinity.omos.service.S3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"이미지 저장 API"})
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service S3Service;

    /**
     * Amazon S3에 파일 여러개 업로드
     * @return 성공 시 200 Success와 함께 업로드 된 파일의 파일명 리스트 반환
     */
    @ApiOperation(value = "Amazon S3에 파일 업로드", notes = "Amazon S3에 파일 업로드 ")
    @PostMapping("/files")
    public ResponseEntity<List<String>> uploadFiles(@ApiParam(value="파일들(여러 파일 업로드 가능)", required = true) @RequestPart List<MultipartFile> multipartFile) {
        return ResponseEntity.ok(S3Service.uploadFiles(multipartFile));
    }

    /**
     * Amazon S3에 파일 업로드
     * @return 성공 시 200 Success와 함께 업로드 된 파일의 파일명 리스트 반환
     */
    @ApiOperation(value = "Amazon S3에 파일 업로드", notes = "Amazon S3에 파일 업로드 ")
    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@ApiParam(value="파일들(여러 파일 업로드 가능)", required = true) @RequestPart MultipartFile multipartFile) {
        return ResponseEntity.ok(S3Service.uploadFile(multipartFile));
    }

    /**
     * Amazon S3에 업로드 된 파일을 삭제
     * @return 성공 시 200 Success
     */
    @ApiOperation(value = "Amazon S3에 업로드 된 파일을 삭제", notes = "Amazon S3에 업로드된 파일 삭제 directory는 이미지가 image파일에 들어있으면 image라고 넣어주세요")
    @DeleteMapping("/file")
    public ResponseEntity<StateDto> deleteFile(@ApiParam(value="파일 하나 삭제", required = true)@RequestParam String directory, @RequestParam String fileName) {
        return ResponseEntity.ok(S3Service.deleteFile(directory,fileName));
    }


}
