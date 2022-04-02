package com.infinity.omos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class ReportDto {
    private Long fromUserId;
    private Long toUserId;
    private Long recordId;
    private String reportReason;
}
