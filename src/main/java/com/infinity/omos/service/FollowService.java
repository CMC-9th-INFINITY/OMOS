package com.infinity.omos.service;

import com.infinity.omos.domain.*;
import com.infinity.omos.domain.Posts.PostsRepository;
import com.infinity.omos.dto.CountDto;
import com.infinity.omos.dto.DjprofileDto;
import com.infinity.omos.dto.MyDjDto;
import com.infinity.omos.dto.StateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final QueryRepository queryRepository;
    private final PostsRepository postsRepository;

    public StateDto save(Long fromUserId, Long toUserId) {
        User fromUser = userRepository.findById(fromUserId).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));
        User toUser = userRepository.findById(toUserId).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));
        if (queryRepository.existsFollowByUserId(fromUser, toUser)) {
            throw new RuntimeException("이미 팔로우가 되어있습니다");
        }
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

    public List<MyDjDto> selectMyDjList(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));

        return queryRepository.findToUserIdBYFromUserId(user)
                .stream()
                .map(User::toMyDjDto)
                .collect(Collectors.toList());

    }

    public DjprofileDto selectFollowCount(Long fromUserId, Long toUserId) {
        Optional<User> optionalUser = userRepository.findById(toUserId);

        if (optionalUser.isPresent()) {
            User existedUser = optionalUser.get();
            MyDjDto myDjDto = existedUser.toMyDjDto();

            return DjprofileDto.builder()
                    .count(CountDto.builder()
                            .followerCount(Integer.toString(followRepository.countByToUserId(existedUser)))
                            .followingCount(Integer.toString(followRepository.countByFromUserId(existedUser)))
                            .recordsCount(Integer.toString(postsRepository.countByUserId(existedUser)))
                            .build())
                    .profile(myDjDto)
                    .isFollowed(queryRepository.existsFollowByUserId(
                            userRepository.getById(fromUserId), existedUser))
                    .build();

        } else {
            throw new RuntimeException("해당 유저는 존재하지 않는 유저입니다.");
        }

    }


}
