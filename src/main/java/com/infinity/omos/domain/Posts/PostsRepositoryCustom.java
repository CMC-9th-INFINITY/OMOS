package com.infinity.omos.domain.Posts;

import com.infinity.omos.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostsRepositoryCustom {
    Page<Posts> findAllByCategory(Category category, Pageable pageable);
}
