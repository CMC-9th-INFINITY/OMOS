package com.infinity.omos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrackDto {
    private String musicId;
    private String musicTitle;
    private List<Artists> artists;
    private String albumImageUrl;
    private String releaseDate;
    private String albumTitle;
    private String albumId;



}
