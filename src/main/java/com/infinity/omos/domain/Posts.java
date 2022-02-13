package com.infinity.omos.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Getter
@AllArgsConstructor
@Entity
@Builder
@NoArgsConstructor
public class Posts extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name="music_id")
    private Music musicId;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false)
    private boolean publicOrNot;

    @Column(nullable = false)
    private String title;

    private String contents;





}
