package com.infinity.omos.domain.Block;

import com.infinity.omos.domain.BaseTimeEntity;
import com.infinity.omos.domain.Posts.Posts;
import com.infinity.omos.domain.Report.Report;
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
public class Block extends BaseTimeEntity {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="report_id")
    private Report reportId;

    public void updateReportId(Report reportId){
        this.reportId = reportId;
    }
}
