package com.infinity.omos.service;

import com.infinity.omos.domain.*;
import com.infinity.omos.dto.StateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final QueryRepository queryRepository;

    public StateDto save(Long fromUserId, Long toUserId) {
        User fromUser = userRepository.findById(fromUserId).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));
        User toUser = userRepository.findById(toUserId).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));
        followRepository.save(
                Follow.builder()
                        .fromUserId(fromUser)
                        .toUserId(toUser)
                        .build()
        );
        return StateDto.builder().state(true).build();
    }

    public StateDto delete(Long fromUserId, Long toUserId) {
        User fromUser = userRepository.findById(fromUserId).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));
        User toUser = userRepository.findById(toUserId).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));

        followRepository.delete(queryRepository.findFollowByUserId(toUser, fromUser));
        return StateDto.builder().state(true).build();
    }






}
