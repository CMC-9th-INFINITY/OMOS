package com.infinity.omos.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@Setter
public class HotTrackDto {
    private String albumImageUrl;
    private String musicTitle;
    private List<String> artistName;
    private String musicId;
}
