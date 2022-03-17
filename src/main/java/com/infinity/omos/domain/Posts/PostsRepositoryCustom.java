package com.infinity.omos.domain.Posts;

import com.infinity.omos.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostsRepositoryCustom {
    Page<Posts> findAllByCategory(Category category, Pageable pageable);
    //List<Posts> findAllByMusicId(Long postId, String musicId, int pageSize);
    Page<Posts> findAllByCategoryOrderByLike(Category category, Pageable pageable);

}
