package com.infinity.omos.service;

import com.infinity.omos.domain.User;
import com.infinity.omos.domain.UserRepository;
import com.infinity.omos.dto.StateDto;
import com.infinity.omos.dto.UserRequestDto;
import com.infinity.omos.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserResponseDto selectUserProfile(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));

        return UserResponseDto.builder()
                .userId(userId)
                .profileUrl(user.getProfileUrl())
                .nickname(user.getNickname())
                .build();
    }

    @Transactional
    public StateDto updateUserProfile(UserRequestDto userRequestDto){
        User user = userRepository.findById(userRequestDto.getUserId()).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));
        user.updateProfile(userRequestDto);
        return StateDto.builder().state(true).build();
    }

    @Transactional
    public StateDto updatePassword(Long userId, String password , PasswordEncoder passwordEncoder){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않는 유저입니다"));
        user.updatePassword(password,passwordEncoder);
        return StateDto.builder().state(true).build();
    }
}
