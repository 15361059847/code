package com.immoc.service;

import com.immoc.dataobject.ProductCategory;
import com.immoc.dataobject.ProductInfo;

import java.util.List;

/**
 * Created by lenovo on 2019/8/14.
 */
public interface CategoryService {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
