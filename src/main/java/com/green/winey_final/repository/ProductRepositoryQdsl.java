package com.green.winey_final.repository;

import com.green.winey_final.admin.model.ProductVo;
import com.green.winey_final.admin.model.QProductVo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

import static com.green.winey_final.common.entity.QProductEntity.productEntity;
import static com.green.winey_final.common.entity.QSaleEntity.saleEntity;

@Component
@RequiredArgsConstructor
public class ProductRepositoryQdsl {

    private final JPAQueryFactory queryFactory;


    public Page<ProductVo> selProductAll(Pageable pageable, String str) {
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
                .offset(pageable.getPageSize())
                .limit(pageable.getOffset())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(productEntity.count())
                .from(productEntity)
                .leftJoin(saleEntity)
                .on(saleEntity.productEntity.eq(productEntity));

        return PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier[] getAllOrderSpecifiers(Pageable pageable) {
//        List<OrderSpecifier> orders = new ArrayList();
        List<OrderSpecifier> orders = new LinkedList<>();
        if(!pageable.getSort().isEmpty()) {
            for(Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                //order의 property값이 스웨거 입력칸 sort의 number
                switch (order.getProperty().toLowerCase()) {
                    case "productId": orders.add(new OrderSpecifier(direction, productEntity.productId)); break;
                    case "salePrice": orders.add(new OrderSpecifier(direction, saleEntity.salePrice)); break;
                    case "sale": orders.add(new OrderSpecifier(direction, saleEntity.sale)); break;
                    case "price": orders.add(new OrderSpecifier(direction, productEntity.price)); break;
                    case "recommend":
                        orders.add(new OrderSpecifier(direction, productEntity.promotion));
                        orders.add(new OrderSpecifier(direction, productEntity.beginner)); break; //
                    case "quantity": orders.add(new OrderSpecifier(direction, saleEntity.sale)); break;
                }
            }
        }
        return orders.stream().toArray(OrderSpecifier[]::new);
    }



}


