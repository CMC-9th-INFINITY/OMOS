package com.infinity.omos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@Setter
public class DjprofileDto {
    private CountDto count;
    private DjDto profile;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isFollowed;


}
