package com.infinity.omos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.infinity.omos.domain.Category;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@Setter

public class PostsDetailResponseDto {
    //노래제목, 가수이름, 앨범제목, 노래아이디, 레코드제목, 레코드날짜, 레코드 내용, 레코드닉네임, 내가 하트 눌렀는지, 내가 스크랩 눌렀는지, 하트는 몇개인지, 별은 몇개인지
    private MusicDto music;

    private Long recordId;
    private String recordTitle;
    private String recordContents;
    private String recordImageUrl;

    private LocalDateTime createdDate;
    private Category category;

    private int viewsCnt;

    private Long userId;
    private String nickname;



    private int likeCnt;
    private int scrapCnt;

    private Boolean isLiked;
    private Boolean isScraped;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isPublic;






}
