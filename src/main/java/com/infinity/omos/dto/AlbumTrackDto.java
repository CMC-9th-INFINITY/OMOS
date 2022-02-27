package com.infinity.omos.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@Setter
public class AlbumTrackDto {
    private String musicId;
    private String musicTitle;
    private List<Artists> artists;
}
