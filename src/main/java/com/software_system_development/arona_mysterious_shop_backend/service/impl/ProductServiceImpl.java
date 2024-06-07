package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.exception.ThrowUtils;
import com.software_system_development.arona_mysterious_shop_backend.mapper.ProductMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductAddRequest;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        Integer providerId = userService.getUserVO(request).getUserId();
        Date productCreateDate = new Date();
        Date productUpdateDate = new Date();

        if (StringUtils.isAnyBlank(productName, productCategoryName) || productPrice == null || stock == null || productIsEnabled == null || providerId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        Product product = new Product();
        BeanUtils.copyProperties(productAddRequest, product);
        product.setProviderId(providerId);
        product.setProductCreateDate(productCreateDate);
        product.setProductUpdateDate(productUpdateDate);
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
        Integer providerId = productUpdateRequest.getProviderId();
        Date productUpdateDate = new Date();

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
        product.setProductUpdateDate(productUpdateDate);
        isValid(product, false);
        boolean result = updateById(product);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return product.getProductId();
    }

    @Override
    public boolean deleteProduct(Integer productId, HttpServletRequest request) {
        // 获取当前登录用户信息
        UserVO loginUser = userService.getUserVO(request);
        // 根据商品ID查询商品信息
        Product product = this.getById(productId);
        if (product == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "商品不存在");
        }
        // 检查当前登录用户是否为商品的供应商
        if (!loginUser.getUserId().equals(product.getProviderId())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无删除权限");
        }
        // 删除商品
        return this.removeById(productId);
    }


    @Override
    public List<Product> getByProductName(String productName) {
        if (StringUtils.isBlank(productName)) {
            return Collections.emptyList();
        }
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("productName", productName);
        return this.list(queryWrapper);
    }

    @Override
    public List<Product> getByProductCategoryName(String productCategoryName) {
        if (StringUtils.isBlank(productCategoryName)) {
            return Collections.emptyList();
        }
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("productCategoryName", productCategoryName);
        return this.list(queryWrapper);
    }

    @Override
    public List<Product> getByProductPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice.compareTo(maxPrice) > 0) {
            return Collections.emptyList();
        }
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("productPrice", minPrice, maxPrice);
        return this.list(queryWrapper);
    }

    @Override
    public List<Product> getByMinProductPrice(BigDecimal minPrice) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("productPrice", minPrice);
        return this.list(queryWrapper);
    }

    @Override
    public List<Product> getByMaxProductPrice(BigDecimal maxPrice) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.le("productPrice", maxPrice);
        return this.list(queryWrapper);
    }


    @Override
    public List<Product> getByProviderId(Integer providerId) {
        if (providerId == null) {
            return Collections.emptyList();
        }
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("providerId", providerId);
        return this.list(queryWrapper);
    }

    @Override
    public List<Product> getByDescription(String description) {
        if (StringUtils.isBlank(description)) {
            return Collections.emptyList();
        }
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("productDescription", description);
        return this.list(queryWrapper);
    }


    public List<ProductVO> getProductVO(List<Product> productList) {
        return productList.stream().map(product -> {
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(product, productVO);
            return productVO;
        }).collect(Collectors.toList());
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
        if (name.length() > 256) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
    }

    @Override
    public BigDecimal getProductPrice(Integer productId) {

        Product product = this.getById(productId);
        if (product == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "商品不存在");
        }
        return product.getProductPrice();
    }
}




