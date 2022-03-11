package com.infinity.omos.domain;

import com.infinity.omos.domain.Posts.Posts;
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
public class Scrap extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_id")
    private User userId;


    @NotNull
    @ManyToOne
    @JoinColumn(name="post_id")
    private Posts postId;
}
