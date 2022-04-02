package com.infinity.omos.domain.RefreshToken;

import com.infinity.omos.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "refresh_token")
public class RefreshToken extends BaseTimeEntity {

    @Id
    @Column(name="user_email",nullable = false)
    private String userEmail;

    private String token;

    public void update(String token) {
        this.token = token;
    }

}