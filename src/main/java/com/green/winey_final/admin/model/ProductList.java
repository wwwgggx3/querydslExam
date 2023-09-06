package com.green.winey_final.admin.model;

import lombok.*;
import org.springframework.data.domain.Pageable;


import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductList {
    private PageDto page;
    private Pageable pageable;
    private List<ProductVo> productList;
}
