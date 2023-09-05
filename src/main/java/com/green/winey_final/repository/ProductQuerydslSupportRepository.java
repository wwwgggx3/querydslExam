package com.green.winey_final.repository;


import com.green.winey_final.common.entity.QProductEntity;
import com.green.winey_final.repository.support.Querydsl4RepositorySupport;

public class ProductQuerydslSupportRepository extends Querydsl4RepositorySupport {
    public ProductQuerydslSupportRepository() {
        super(QProductEntity.class);
    }
}
