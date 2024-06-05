package com.software_system_development.arona_mysterious_shop_backend.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductDeleteRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductQueryRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductUpdateRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.User;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.ProductVO;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.logging.Handler;

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
     * @param deleteRequest
     * @param request
     * @return
     */
    boolean removeProduct(ProductDeleteRequest deleteRequest, HttpServletRequest request);

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
     * 获取查询条件包装器
     * @param productQueryRequest
     * @return
     */
    Wrapper<Product> getQueryWrapper(ProductQueryRequest productQueryRequest);

    /**
     * 模糊查询商品信息
     * @param productQueryRequest
     * @return
     */
    Page<ProductVO> listProductsByPage(ProductQueryRequest productQueryRequest);

    /**
     * 校验商品信息合法性
     * @param product
     * @param add
     */
    void isValid(Product product, boolean add);
}
