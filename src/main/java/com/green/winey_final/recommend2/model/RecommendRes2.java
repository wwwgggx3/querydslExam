package com.green.winey_final.recommend2.model;

import lombok.Data;

import java.util.List;

@Data
public class RecommendRes2 {
    private List<Long> categoryId;
    private List<Long> countryId;
    private List<Long> smallCategoryId;
    private List<Long> priceRange;
    private List<Long> aromaCategoryId;
}
