package com.infinity.omos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@Setter
public class MusicDto {
    private String musicId;
    private String musicTitle;
    private List<Artists> artists;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String albumTitle;

    private String albumImageUrl;
    //노래제목, 가수이름, 앨범제목, 노래아이디,

}
