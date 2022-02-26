package com.infinity.omos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class PostsDetailResponseDto {
    //노래제목, 가수이름, 앨범제목, 노래아이디, 레코드제목, 레코드날짜, 레코드 내용, 레코드닉네임, 내가 하트 눌렀는지, 내가 스크랩 눌렀는지, 하트는 몇개인지, 별은 몇개인지
    private MusicDto music;

    private Long recordId;
    private String recordTitle;
    private String recordContents;
    private LocalDateTime createdDate;
    private int viewsCnt;

    private Long userId;
    private String nickname;



    private int likeCnt;
    private int scrapCnt;

    private boolean isLiked;
    private boolean isScraped;




}
