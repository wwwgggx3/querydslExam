package com.green.winey_final.repository;

import com.green.winey_final.admin.model.*;
import com.green.winey_final.repository.support.PageCustom;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminQdslRepository {
    PageCustom<ProductVo> selProductAll(Pageable pageable, String str);
    PageCustom<UserVo> selUserAll(Pageable pageable, String searchType, String str);


    PageCustom<UserOrderDetailVo> selUserOrderByUserId(Long userId, Pageable pageable);//고쳐야함
    UserInfo selUserInfoByUserId(Long userId, Pageable pageable);

//    PageCustom<OrderListVo> selOrderAll(Pageable pageable);
    PageCustom<OrderListVo> selOrderAll(Pageable pageable); //안됨

    PageCustom<StoreVo> selStoreAll(Pageable pageable, String searchType, String str);

}
