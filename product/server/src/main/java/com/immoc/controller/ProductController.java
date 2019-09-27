package com.immoc.controller;

import com.immoc.dataobject.ProductCategory;
import com.immoc.dataobject.ProductInfo;
import com.immoc.dto.CartDTO;
import com.immoc.service.CategoryService;
import com.immoc.service.ProductService;
import com.immoc.utils.ResultVOUtil;
import com.immoc.vo.ProductInfoVo;
import com.immoc.vo.ProductVo;
import com.immoc.vo.ResultVo;
import com.imooc.product.common.DecreaseStockInput;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    /**
     *  1、查询所有在架商品
     *  2、获取类目type列表
     *  3、查询类目
     *  4、构造数据
     *  CrossOrigin实现跨域注解
     * */
    @GetMapping("/list")
    @CrossOrigin
    public ResultVo<ProductVo> list(){
        //1. 查询所有在架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        //2. 获取类目type列表
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());

        //3. 从数据库查询类目
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //4. 构造数据
        List<ProductVo> productVOList = new ArrayList<>();
        for (ProductCategory productCategory: categoryList) {
            ProductVo productVo = new ProductVo();
            productVo.setCategoryName(productCategory.getCategoryName());
            productVo.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVo> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo: productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVo productInfoVO = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVo.setProductIndoVoList(productInfoVOList);
            productVOList.add(productVo);
        }
        return ResultVOUtil.success(productVOList);
    }


    /**
     * 获取商品列表（订单服务）
     * @param productIdList
     * @return
     */
    @GetMapping("/listForOrder")
    public List<ProductInfo> listForOrder(@RequestBody List<String> productIdList){
        return productService.findByProductIdIn(productIdList);
    }



    /**
     * 扣除商品库存
     * @param cartDTOList
     * @return
     */
    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<CartDTO> cartDTOList) {
        productService.decreaseStock(cartDTOList);
    }

}
