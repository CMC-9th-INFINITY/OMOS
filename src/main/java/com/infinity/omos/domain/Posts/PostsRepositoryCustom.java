package com.infinity.omos.domain.Posts;

import com.infinity.omos.domain.Category;
import com.infinity.omos.domain.Posts.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostsRepositoryCustom {
    Page<Posts> findAllByCategory(Category category, Pageable pageable);
}
