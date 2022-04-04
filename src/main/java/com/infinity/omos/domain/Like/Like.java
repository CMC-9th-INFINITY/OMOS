package com.infinity.omos.domain.Like;


import com.infinity.omos.domain.BaseTimeEntity;
import com.infinity.omos.domain.Posts.Posts;
import com.infinity.omos.domain.User.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@Entity
@Builder
@NoArgsConstructor
@Table(name = "like_num")
public class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @NotNull
    private User userId;

    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    @NotNull
    private Posts postId;



}
