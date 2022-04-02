package com.infinity.omos.domain.Posts;

import com.infinity.omos.domain.BaseTimeEntity;
import com.infinity.omos.domain.Category;
import com.infinity.omos.domain.Music.Music;
import com.infinity.omos.domain.User.User;
import com.infinity.omos.dto.PostsUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Entity
@Builder
@NoArgsConstructor
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id")
    private Music musicId;

    @Column(nullable = false, name = "category_id")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false, name = "is_public")
    private Boolean isPublic;

    @Column(nullable = false)
    private String title;

    private String contents;

    private String imageUrl;

    private int cnt;

    public void updateCnt() {
        ++cnt;
    }

    public void updatePublic() {
        this.isPublic = !this.isPublic;
    }

    public void updatePosts(PostsUpdateDto postsUpdateDto){
        this.title = postsUpdateDto.getTitle();
        this.contents = postsUpdateDto.getContents();
        this.imageUrl = postsUpdateDto.getRecordImageUrl();
        this.isPublic = postsUpdateDto.getIsPublic();
    }

}
