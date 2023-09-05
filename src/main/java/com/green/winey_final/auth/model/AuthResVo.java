package com.green.winey_final.auth.model;

import com.green.winey_final.common.config.security.model.RoleType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResVo {
    private String accessToken;
}
