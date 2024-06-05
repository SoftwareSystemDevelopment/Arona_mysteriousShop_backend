package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.exception.ThrowUtils;
import com.software_system_development.arona_mysterious_shop_backend.mapper.ProductMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductDeleteRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductQueryRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductUpdateRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Product;
import com.software_system_development.arona_mysterious_shop_backend.model.enums.CategoryEnum;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.ProductVO;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import com.software_system_development.arona_mysterious_shop_backend.service.ProductService;
import com.software_system_development.arona_mysterious_shop_backend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
* @author 29967
* @description 针对表【product】的数据库操作Service实现
* @createDate 2024-06-05 12:28:45
*/
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
    implements ProductService{

    @Resource
    UserService userService;

    @Override
    public int addProduct(ProductAddRequest productAddRequest, HttpServletRequest request) {
        String productName = productAddRequest.getProductName();
        String productCategoryName = productAddRequest.getProductCategoryName();
        BigDecimal productPrice = productAddRequest.getProductPrice();
        Integer stock = productAddRequest.getStock();

        Integer productIsEnabled = productAddRequest.getProductIsEnabled();
        if (StringUtils.isAnyBlank(productName, productCategoryName) || productPrice == null || stock == null || productIsEnabled == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        Product product = new Product();
        BeanUtils.copyProperties(productAddRequest, product);
        isValid(product, true);
        UserVO loginUser = userService.getUserVO(request);
        product.setProviderId(loginUser.getUserId());
        boolean result = save(product);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return product.getProductId();
    }

    @Override
    public int updateProduct(ProductUpdateRequest productUpdateRequest, HttpServletRequest request) {
        Integer productId = productUpdateRequest.getProductId();
        String productName = productUpdateRequest.getProductName();
        String productCategoryName = productUpdateRequest.getProductCategoryName();
        BigDecimal productPrice = productUpdateRequest.getProductPrice();
        Integer stock = productUpdateRequest.getStock();
        Integer productIsEnabled = productUpdateRequest.getProductIsEnabled();
        Long providerId = productUpdateRequest.getProviderId();
        if (productId == null || StringUtils.isAnyBlank(productName, productCategoryName) || productPrice == null || stock == null || productIsEnabled == null || providerId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        //不是自己上架的商品不允许修改
        UserVO loginUser = userService.getUserVO(request);
        if(!loginUser.getUserId().equals(providerId)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无修改权限");
        }
        Product product = new Product();
        BeanUtils.copyProperties(productUpdateRequest, product);
        isValid(product, false);
        boolean result = updateById(product);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return product.getProductId();
    }

    @Override
    public boolean removeProduct(ProductDeleteRequest deleteRequest, HttpServletRequest request) {
        Long providerId = deleteRequest.getProviderId();
        UserVO loginUser = userService.getUserVO(request);
        if(!loginUser.getUserId().equals(providerId)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无修改权限");
        }
        return removeById(deleteRequest.getProductId());
    }

    @Override
    public Product getProduct(int id, HttpServletRequest request) {
        UserVO loginUser = userService.getUserVO(request);
        Product product = this.getById(id);
        if (product == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "商品不存在");
        }
        if (!loginUser.getUserId().equals(product.getProviderId())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无查看权限");
        }
        return product;
    }

    @Override
    public ProductVO getProductVO(int id) {
        Product product = this.getById(id);
        if (product == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "商品不存在");
        }
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(product, productVO);
        return productVO;
    }


    @Override
    public Wrapper<Product> getQueryWrapper(ProductQueryRequest productQueryRequest) {
        if (productQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Integer productId = productQueryRequest.getProductId();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(productId != null, "productId", productId);
        queryWrapper.orderByAsc("productId");
        return queryWrapper;
    }

    @Override
    public Page<ProductVO> listProductsByPage(ProductQueryRequest productQueryRequest) {
        long current = productQueryRequest.getCurrent();
        long size = productQueryRequest.getPageSize();
        // 构建查询条件
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        // 添加其他条件，如商品名称模糊搜索
        if (StringUtils.isNotBlank(productQueryRequest.getProductName())) {
            queryWrapper.like("productName", productQueryRequest.getProductName());
        }
        Page<Product> productPage = page(new Page<>(current, size), queryWrapper);
        // 转换为VO列表
        List<Product> productList = productPage.getRecords();
        List<ProductVO> productVOList = new ArrayList<>();
        for (Product product : productList) {
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(product, productVO);
            productVOList.add(productVO);
        }
        // 构建VO分页对象
        Page<ProductVO> productVOPage = new Page<>();
        BeanUtils.copyProperties(productPage, productVOPage);
        productVOPage.setRecords(productVOList);
        return productVOPage;
    }


    @Override
    public void isValid(Product product, boolean add) {
        if (product == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String name = product.getProductName();
        String productCategoryName = product.getProductCategoryName();
        BigDecimal price = product.getProductPrice();
        Integer stock = product.getStock();
        Integer productIsEnabled = product.getProductIsEnabled();

        ThrowUtils.throwIf(stock < 0, ErrorCode.PARAMS_ERROR,"库存不能小于0");
        ThrowUtils.throwIf(price.intValue() < 0, ErrorCode.PARAMS_ERROR,"价格不能小于0");
        ThrowUtils.throwIf(productIsEnabled != 0 && productIsEnabled != 1, ErrorCode.PARAMS_ERROR,"商品状态错误");
        ThrowUtils.throwIf(!CategoryEnum.contains(productCategoryName), ErrorCode.PARAMS_ERROR,"商品类型错误");

        Product productByName = this.lambdaQuery().eq(Product::getProductName, name).one();
        if (productByName != null && !productByName.getProductId().equals(product.getProductId())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "产品名称已存在");
        }
        if (name.length() > 256) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
    }
}




