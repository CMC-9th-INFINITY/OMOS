package com.infinity.omos.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@Setter
public class AlbumsDto {
    private String musicId;
    private String title;
    private String artist;
    private String artistId;
    private String albumImageUrl;
    private String releaseDate;
    private String albumName;
    private String albumId;



}
