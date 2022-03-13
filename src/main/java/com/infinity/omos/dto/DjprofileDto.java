package com.infinity.omos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class DjprofileDto {
    private CountDto count;
    private DjDto profile;
    private Boolean isFollowed;
}
