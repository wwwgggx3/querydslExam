package com.green.winey_final.repository;

import com.green.winey_final.admin.model.ProductVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductRepositoryCustom {

    List<ProductVo> selProductAll(Pageable pageable, String search);
}
