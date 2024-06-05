package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.review.ReviewAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.review.ReviewDeleteRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Review;
import com.software_system_development.arona_mysterious_shop_backend.model.enums.UserRoleEnum;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import com.software_system_development.arona_mysterious_shop_backend.service.ReviewService;
import com.software_system_development.arona_mysterious_shop_backend.mapper.ReviewMapper;
import com.software_system_development.arona_mysterious_shop_backend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 29967
* @description 针对表【review】的数据库操作Service实现
* @createDate 2024-06-05 12:28:45
*/
@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {

    @Resource
    UserService userService;

    @Override
    public long addReview(ReviewAddRequest reviewAddRequest) {
        if (reviewAddRequest == null || reviewAddRequest.getReviewContent() == null || reviewAddRequest.getReviewUserId() == null || reviewAddRequest.getReviewProductId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        Review review = new Review();
        BeanUtils.copyProperties(reviewAddRequest, review);
        review.setReviewcreatedate(new Date());

        boolean saveResult = this.save(review);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "添加评论失败");
        }

        return review.getReviewid();
    }

    @Override
    public boolean deleteReview(ReviewDeleteRequest reviewDeleteRequest, HttpServletRequest request) {
        if (reviewDeleteRequest == null || reviewDeleteRequest.getReviewId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }

        Review review = this.getById(reviewDeleteRequest.getReviewId());
        if (review == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "评论未找到");
        }

        // 从请求中获取当前用户信息
        UserVO loginUser = userService.getUserVO(request);
        String userRole = loginUser.getUserRole();

        // 验证用户权限
        if (!review.getReviewuserid().equals(loginUser.getUserId()) && !UserRoleEnum.ADMIN.getValue().equals(userRole)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限删除");
        }

        boolean removeResult = this.removeById(reviewDeleteRequest.getReviewId());
        if (!removeResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除评论失败");
        }

        return true;
    }


    @Override
    public List<Review> getReviewsByProductId(Integer productId) {
        if (productId == null || productId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }

        QueryWrapper<Review> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("reviewproductid", productId);

        List<Review> reviewList = this.list(queryWrapper);
        if (reviewList == null || reviewList.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "未找到评论");
        }

        return reviewList;
    }
}




