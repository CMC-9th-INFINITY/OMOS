package com.infinity.omos.domain.Posts;

import com.infinity.omos.domain.Category;
import com.infinity.omos.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long>, PostsRepositoryCustom{

    Page<Posts> findByCategoryOrderByCreatedDateDesc(Category category, Pageable pageable);
    int countByUserId(User user);


}

