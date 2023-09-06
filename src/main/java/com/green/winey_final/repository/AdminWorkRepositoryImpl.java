package com.green.winey_final.repository;

import com.green.winey_final.admin.model.ProductVo;
import com.green.winey_final.repository.support.PageCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminWorkRepositoryImpl implements AdminQdslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PageCustom<ProductVo> selProductAll(Pageable pageable, String str) {
        return null;
    }
}
