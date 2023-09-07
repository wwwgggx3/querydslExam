package com.green.winey_final.repository;

import com.green.winey_final.admin.model.ProductVo;
import com.green.winey_final.admin.model.QProductVo;
import com.green.winey_final.admin.model.QUserVo;
import com.green.winey_final.admin.model.UserVo;
import com.green.winey_final.repository.support.PageCustom;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

import static com.green.winey_final.common.entity.QProductEntity.productEntity;
import static com.green.winey_final.common.entity.QRegionNmEntity.regionNmEntity;
import static com.green.winey_final.common.entity.QSaleEntity.saleEntity;
import static com.green.winey_final.common.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class AdminWorkRepositoryImpl implements AdminQdslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public PageCustom<ProductVo> selProductAll(Pageable pageable, String str) {

        BooleanBuilder whereBuilder = new BooleanBuilder();
        if(str != null) {
            whereBuilder.and(productEntity.nmKor.contains(str));
        }

        List<ProductVo> list = queryFactory.select(new QProductVo(productEntity.productId, productEntity.nmKor, productEntity.price, productEntity.promotion, productEntity.beginner, productEntity.quantity, saleEntity.sale, saleEntity.salePrice))
                .from(productEntity)
                .leftJoin(saleEntity)
                .on(saleEntity.productEntity.eq(productEntity))
                .orderBy(getAllOrderSpecifiers(pageable))
                .where(whereBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(productEntity.count())
                .from(productEntity)
                .leftJoin(saleEntity)
                .on(saleEntity.productEntity.eq(productEntity));

        Page<ProductVo> map = PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);

        return new PageCustom<ProductVo>(map.getContent(), map.getPageable(), map.getTotalElements());
    }

    @Override
    public PageCustom<UserVo> selUserAll(Pageable pageable, String searchType, String str) {

        List<UserVo> list = queryFactory.select(new QUserVo(userEntity.userId, userEntity.email, userEntity.unm, regionNmEntity.regionNmId.intValue(), userEntity.createdAt.stringValue()))
                .from(userEntity)
                .orderBy(getAllOrderSpecifiers(pageable))
                .where(eqUserName(searchType, str),
                       eqUserEmail(searchType, str))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(userEntity.count())// count()와 countDistinct() 차이 알기
                .from(userEntity);

        Page<UserVo> map = PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);

        return new PageCustom<UserVo>(map.getContent(), map.getPageable(), map.getTotalElements());
    }


    //정렬
    private OrderSpecifier[] getAllOrderSpecifiers(Pageable pageable) {
//        List<OrderSpecifier> orders = new ArrayList();
        List<OrderSpecifier> orders = new LinkedList<>();
        if(!pageable.getSort().isEmpty()) {
            for(Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                String str=order.getProperty();
                //order의 property값이 스웨거 입력칸 sort의 number
                switch (order.getProperty().toLowerCase()) {
                    case "productid": orders.add(new OrderSpecifier(direction, productEntity.productId)); break;
                    case "saleprice": orders.add(new OrderSpecifier(direction, saleEntity.salePrice)); break;
                    case "sale": orders.add(new OrderSpecifier(direction, saleEntity.sale)); break;
                    case "price": orders.add(new OrderSpecifier(direction, productEntity.price)); break;
                    case "recommend":
                        orders.add(new OrderSpecifier(direction, productEntity.promotion));
                        orders.add(new OrderSpecifier(direction, productEntity.beginner)); break; //
                    case "quantity": orders.add(new OrderSpecifier(direction, productEntity.quantity)); break;

                    //가입회원 리스트 정렬
                    case "userid": orders.add(new OrderSpecifier(direction, userEntity.userId)); break;
                    case "pickup": orders.add(new OrderSpecifier(direction, userEntity.regionNmEntity.regionNmId)); break;


                }
            }
        }
        return orders.stream().toArray(OrderSpecifier[]::new);
    }



    //동적 검색 조건
    /*
    equalsIgnoreCase() 대소문자 구분없이 비교
    equals() 대소문자 구분해서 비교
     */
    //유저 검색 조건
    public BooleanExpression eqUserName(String searchType, String str) {
        if (searchType == null) {
            return null;
        } else if (searchType.equalsIgnoreCase("unm")) {
            return userEntity.unm.containsIgnoreCase(str);
        }
        return null;

    }

    public BooleanExpression eqUserEmail(String searchType, String str) {
        if (searchType == null) {
            return null;
        } else if (searchType.equalsIgnoreCase("email")) {
            return userEntity.email.containsIgnoreCase(str);
        }
        return null;

    }


}