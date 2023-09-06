package com.green.winey_final.repository.support;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Component;

import static com.green.winey_final.common.entity.QUserEntity.userEntity;
@Component
public class DynamicQueryWhere {



    public BooleanExpression eqUserName(String str) {
//        if (StringUtils.isBlank(str)) {
//            return null;
//        }
        return str.isBlank() ? null : userEntity.unm.containsIgnoreCase(str);
    }

    public BooleanExpression eqUserEmail(String str) {
//        if (StringUtils.isBlank(str)) {
//            return null;
//        }
        return str.isBlank() ? null : userEntity.email.containsIgnoreCase(str);
    }
}
