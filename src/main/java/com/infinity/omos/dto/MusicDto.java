package com.infinity.omos.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@Setter
public class MusicDto {
    private String musicId;
    private String artistId;
    private String musicTitle;
    private String artistName;
    private String albumTitle;
    private String albumImageUrl;


}
