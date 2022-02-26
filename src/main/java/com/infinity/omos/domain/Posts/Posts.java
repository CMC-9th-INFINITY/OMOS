package com.infinity.omos.domain.Posts;

import com.infinity.omos.domain.BaseTimeEntity;
import com.infinity.omos.domain.Category;
import com.infinity.omos.domain.Music;
import com.infinity.omos.domain.User;
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

    @ManyToOne
    @JoinColumn(name="user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name="music_id")
    private Music musicId;

    @Column(nullable = false,name = "category_id")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private boolean publicOrNot;

    @Column(nullable = false)
    private String title;

    private String contents;


    private int cnt;




}
