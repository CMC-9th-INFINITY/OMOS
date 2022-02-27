package com.infinity.omos.domain;

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
public class Music extends BaseTimeEntity{

    @Id
    @Column(nullable = false)
    private String id;

    //@Column(nullable = false)
    private String artistId;
}
