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
public class MyRecordDto {
    private MusicDto music;
    private Long recordId;
    private String recordTitle;
    private String recordContents;
    private LocalDateTime createdDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isPublic;

    private Category category;
    //private String recordImageUrl;

}
