package com.infinity.omos.service;

import com.infinity.omos.domain.Block.Block;
import com.infinity.omos.domain.Block.BlockRepository;
import com.infinity.omos.domain.Posts.Posts;
import com.infinity.omos.domain.Posts.PostsRepository;
import com.infinity.omos.domain.Report.Report;
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
public class BlockService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final BlockRepository blockRepository;

    @Transactional
    public StateDto save(ReportType type, ReportDto reportDto, Report report) {
        Block block;

        User fromUser = userRepository.findById(reportDto.getFromUserId()).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));


        if (type == ReportType.Record) {
            Posts posts = postsRepository.findById(reportDto.getRecordId()).orElseThrow(() -> new RuntimeException("해당 레코드는 존재하지 않는 레코드입니다"));
            block = savePostType(fromUser, posts);
        } else {
            User toUser = userRepository.findById(reportDto.getToUserId()).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));
            block = saveUserType(fromUser, toUser);
        }

        blockRepository.save(block);

        block.updateReportId(report);

        return StateDto.builder().state(true).build();
    }

    @Transactional
    public Block saveUserType(User fromUser, User toUser) {
        return Block.builder()
                .reportType(ReportType.User)
                .fromUserId(fromUser)
                .toUserId(toUser)
                .build();
    }

    @Transactional
    public Block savePostType(User fromUser, Posts posts) {
        return Block.builder()
                .reportType(ReportType.Record)
                .fromUserId(fromUser)
                .postId(posts)
                .build();
    }

    @Transactional
    public StateDto delete(Long blockId){
        Block block = blockRepository.findById(blockId).orElseThrow(() -> new RuntimeException("해당 차단은 존재하지 않는 차단입니다"));
        blockRepository.delete(block);

        return StateDto.builder().state(true).build();
    }
}
