package com.infinity.omos.service;

import com.infinity.omos.domain.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional(readOnly = true)
    public void selectAllRecords(){

    }

}
