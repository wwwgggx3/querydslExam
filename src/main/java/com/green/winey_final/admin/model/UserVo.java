package com.green.winey_final.admin.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserVo {
    Long userId;
    String email;
    String unm;
    int regionNmId;
    String createdAt;

    @QueryProjection
    public UserVo(Long userId, String email, String nm, int regionNmId, String createdAt) {
        this.userId = userId;
        this.email = email;
        this.unm = nm;
        this.regionNmId = regionNmId;
        this.createdAt = createdAt;
    }
}
