package com.software_system_development.arona_mysterious_shop_backend.service;

import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductUpdateRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Product;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.ProductVO;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 29967
* @description 针对表【product】的数据库操作Service
* @createDate 2024-06-05 12:28:45
*/
public interface ProductService extends IService<Product> {


    /**
     * 增加商品
     * @param productAddRequest
     * @return
     */
    int addProduct(ProductAddRequest productAddRequest, HttpServletRequest request);

    /**
     * 修改商品信息
     * @param productUpdateRequest
     * @param request
     * @return
     */
    int updateProduct(ProductUpdateRequest productUpdateRequest, HttpServletRequest request);

    /**
     * 删除商品
     * @param request
     * @return
     */
    boolean removeProduct(Integer productId, HttpServletRequest request);

    /**
     * 根据条件查询商品列表
     * @param productName
     * @return
     */
    List<Product> getByProductName(String productName);
    List<Product> getByProductCategoryName(String productCategoryName);
    List<Product> getByProductPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    List<Product> getByMinProductPrice(BigDecimal minPrice);
    List<Product> getByMaxProductPrice(BigDecimal maxPrice);
    List<Product> getByProviderId(Integer providerId);
    List<Product> getByDescription(String description);

    List<ProductVO> getProductVO(List<Product> productList);

    /**
     * 校验商品信息合法性
     * @param product
     * @param add
     */
    void isValid(Product product, boolean add);


}
