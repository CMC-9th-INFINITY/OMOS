package com.infinity.omos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class AllSearchDto {
    private List<AlbumDto> albumDtos;
    private List<ArtistDto> artistDtos;
    private List<TrackDto> trackDtos;
}
