package com.software_system_development.arona_mysterious_shop_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductUpdateRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Product;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.PageVO;
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
    boolean deleteProduct(Integer productId, HttpServletRequest request);


    /**
     *  获取商品信息
     * @param id
     * @param request
     * @return
     */
    Product getProduct(int id, HttpServletRequest request);

    /**
     * 获取商品信息VO
     * @param id
     * @return
     */
    ProductVO getProductVO(int id);

    /**
     * 根据条件查询商品列表
     * @param productName
     * @param productCategory
     * @param minPrice
     * @param maxPrice
     * @param productDescription
     * @return
     */
    QueryWrapper<Product> getQueryWrapper(String productName, String productCategory, BigDecimal minPrice, BigDecimal maxPrice, String productDescription);

    /**
     * 获取商品列表VO
     * @param productList
     * @return
     */
    List<ProductVO> getProductVO(List<Product> productList);

    /**
     * 根据供应商ID获取商品列表
     * @param providerId
     * @return
     */
    List<Product> getByProviderId(Integer providerId);

    /**
     * 校验商品信息合法性
     * @param product
     * @param add
     */
    void isValid(Product product, boolean add);

    /**
     *  获取商品的价格
     */
    BigDecimal getProductPrice(Integer productId);

    /**
     * 获取商品的名称
     * @param productId
     * @return
     */
    String getProductName(Integer productId);
}
