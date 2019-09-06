package com.immoc.service.impl;

import com.immoc.dataobject.ProductCategory;
import com.immoc.dataobject.ProductInfo;
import com.immoc.enums.ProductStatusEnum;
import com.immoc.repository.ProductCategoryRepository;
import com.immoc.repository.ProductInfoRepository;
import com.immoc.service.CategoryService;
import com.immoc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lenovo on 2019/8/14.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;


    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
    }
}
