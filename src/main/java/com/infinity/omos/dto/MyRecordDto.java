package com.infinity.omos.dto;

import com.infinity.omos.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class MyRecordDto {
    private MusicDto music;
    private Long recordId;
    private String recordTitle;
    private String recordContents;
    private LocalDateTime createdDate;
    private Boolean isPublic;
    private Category category;

}
