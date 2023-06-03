package com.infinity.omos.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpotifyToken {

    @Id
    private Long id;

    @Column(name="token",nullable = false)
    private String token;
    @Column(name="expiration_time",nullable = false)
    private LocalDateTime expirationTime;


    @PrePersist
    public void prePersist() {
        this.id = 1L;
    }

}
