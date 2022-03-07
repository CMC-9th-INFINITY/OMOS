package com.infinity.omos.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@Setter

public class ArtistDto {

    private String artistName;
    private String artistId;
    private String artistImageUrl;
    private List<String> genres;

}
