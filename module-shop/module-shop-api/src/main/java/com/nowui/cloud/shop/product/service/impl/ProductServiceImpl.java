package com.nowui.cloud.shop.product.service.impl;

import com.nowui.cloud.mybatisplus.BaseWrapper;
import com.nowui.cloud.service.impl.BaseServiceImpl;
import com.nowui.cloud.shop.product.entity.Product;
import com.nowui.cloud.shop.product.mapper.ProductMapper;
import com.nowui.cloud.shop.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author ZhongYongQiang
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public Integer countForAdmin(String appId, String productName) {
        Integer count = count(
                new BaseWrapper<Product>()
                        .eq(Product.APP_ID, appId)
                        .likeAllowEmpty(Product.PRODUCT_NAME, productName)
                        .eq(Product.SYSTEM_STATUS, true)
        );
        return count;
    }

    @Override
    public List<Product> listForAdmin(String appId, String productName, Integer pageIndex, Integer pageSize) {
        List<Product> productList = list(
                new BaseWrapper<Product>()
                        .eq(Product.APP_ID, appId)
                        .like(Product.PRODUCT_NAME, productName)
                        .eq(Product.SYSTEM_STATUS, true)
                        .orderDesc(Arrays.asList(Product.SYSTEM_CREATE_TIME)),
                pageIndex,
                pageSize
        );

        for(Product product: productList) {
            //image list
            //product
        }

        return productList;
    }

}