package com.infinity.omos.service;

import com.infinity.omos.domain.Block.Block;
import com.infinity.omos.domain.Block.BlockRepository;
import com.infinity.omos.domain.Posts.Posts;
import com.infinity.omos.domain.Posts.PostsRepository;
import com.infinity.omos.domain.QueryRepository;
import com.infinity.omos.domain.Report.Report;
import com.infinity.omos.domain.ReportType;
import com.infinity.omos.domain.User.User;
import com.infinity.omos.domain.User.UserRepository;
import com.infinity.omos.dto.ReportDto;
import com.infinity.omos.dto.StateDto;
import com.infinity.omos.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final BlockRepository blockRepository;
    private final QueryRepository queryRepository;

    @Transactional
    public StateDto save(ReportType type, ReportDto reportDto, Report report) {
        Block block;

        User fromUser = userRepository.getById(reportDto.getFromUserId());


        if (type == ReportType.Record) {
            Posts posts = postsRepository.getById(reportDto.getRecordId());
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
    public StateDto delete(Long fromUserId, Long toUserId) {
        User fromUser = userRepository.findById(fromUserId).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));
        User toUser = userRepository.findById(toUserId).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));

        Block block = blockRepository.findByFromUserIdAndToUserId(fromUser,toUser).orElseThrow(() -> new RuntimeException("해당 차단은 존재하지 않는 차단입니다"));
        blockRepository.delete(block);

        return StateDto.builder().state(true).build();
    }

    @Transactional
    public List<UserRequestDto> getByUserId(Long fromUserId) {
        User fromUser = userRepository.findById(fromUserId).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));
        return queryRepository.selectBlockList(fromUser);
    }

}
