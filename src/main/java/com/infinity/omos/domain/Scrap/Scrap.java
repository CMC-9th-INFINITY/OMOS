package com.infinity.omos.domain.Scrap;

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
public class Scrap extends BaseTimeEntity {

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
