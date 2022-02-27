package com.infinity.omos.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@Setter
public class AlbumDto {

    private List<Artists> artists;
    private String albumId;
    private String albumImageUrl;
    private String albumTitle;
    private String releaseDate;


}
