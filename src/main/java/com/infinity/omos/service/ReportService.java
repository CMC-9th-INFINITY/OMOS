package com.infinity.omos.service;

import com.infinity.omos.domain.Block.Block;
import com.infinity.omos.domain.Block.BlockRepository;
import com.infinity.omos.domain.Posts.Posts;
import com.infinity.omos.domain.Posts.PostsRepository;
import com.infinity.omos.domain.Report.Report;
import com.infinity.omos.domain.Report.ReportRepository;
import com.infinity.omos.domain.ReportType;
import com.infinity.omos.domain.User.User;
import com.infinity.omos.domain.User.UserRepository;
import com.infinity.omos.dto.ReportDto;
import com.infinity.omos.dto.StateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final UserRepository userRepository;
    private final PostsRepository postsRepository;
    private final BlockRepository blockRepository;
    private final ReportRepository reportRepository;
    private final BlockService blockService;

    @Transactional
    public Report save(ReportType type, ReportDto reportDto){
        Report report;

        User fromUser = userRepository.findById(reportDto.getFromUserId()).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));


        if (type == ReportType.Record) {
            Posts posts = postsRepository.findById(reportDto.getRecordId()).orElseThrow(() -> new RuntimeException("해당 레코드는 존재하지 않는 레코드입니다"));
            report = savePostType(fromUser, posts ,reportDto.getReportReason());
        } else {
            User toUser = userRepository.findById(reportDto.getToUserId()).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));
            report = saveUserType(fromUser, toUser, reportDto.getReportReason());
        }

        reportRepository.save(report);
        return report;
    }

    @Transactional
    public Report saveUserType(User fromUser, User toUser, String reportReason) {
        return Report.builder()
                .reportType(ReportType.User)
                .fromUserId(fromUser)
                .toUserId(toUser)
                .reportReason(reportReason)
                .build();
    }

    @Transactional
    public Report savePostType(User fromUser, Posts posts, String reportReason) {
        return Report.builder()
                .reportType(ReportType.Record)
                .fromUserId(fromUser)
                .postId(posts)
                .reportReason(reportReason)
                .build();
    }
}
