package com.infinity.omos.domain.Report;

import com.infinity.omos.domain.Posts.Posts;
import com.infinity.omos.domain.ReportType;
import com.infinity.omos.domain.User.User;
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
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportType reportType;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="from_user_id")
    @Column(nullable = false)
    private User fromUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="to_user_id")
    private User toUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Posts postId;

    private String reportReason;

}
