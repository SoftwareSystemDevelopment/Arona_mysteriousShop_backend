package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.software_system_development.arona_mysterious_shop_backend.common.BaseResponse;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.common.ResultUtils;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.review.ReviewAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.review.ReviewDeleteRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.review.ReviewQueryRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Review;
import com.software_system_development.arona_mysterious_shop_backend.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论接口
 *
 */
@RestController
@RequestMapping("/review")
@Slf4j
@Tag(name = "评论接口")
public class ReviewController {

    @Resource
    private ReviewService reviewService;

    /**
     * 增加评论
     *
     * @param reviewAddRequest 评论添加请求
     * @return {@link BaseResponse}<{@link Long}>
     */
    @PostMapping("/add")
    @Operation(summary = "增加评论")
    public BaseResponse<Long> addReview(@RequestBody ReviewAddRequest reviewAddRequest) {
        if (reviewAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long result = reviewService.addReview(reviewAddRequest);
        return ResultUtils.success(result);
    }

    /**
     * 删除评论
     *
     * @param reviewDeleteRequest 评论删除请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping("/delete")
    @Operation(summary = "删除评论")
    public BaseResponse<Boolean> deleteReview(@RequestBody ReviewDeleteRequest reviewDeleteRequest, HttpServletRequest request) {
        if (reviewDeleteRequest == null || reviewDeleteRequest.getReviewId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }
        boolean result = reviewService.deleteReview(reviewDeleteRequest, request);
        return ResultUtils.success(result);
    }

    /**
     * 查询某商品下的所有评论
     *
     * @param reviewQueryRequest 评论查询请求
     * @return {@link BaseResponse}<{@link List}<{@link Review}>>
     */
    @GetMapping("/list")
    @Operation(summary = "查询某商品下的所有评论")
    public BaseResponse<List<Review>> listReviews(@RequestBody ReviewQueryRequest reviewQueryRequest) {
        if (reviewQueryRequest == null || reviewQueryRequest.getProductId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Review> reviewList = reviewService.getReviewsByProductId(reviewQueryRequest.getProductId());
        return ResultUtils.success(reviewList);
    }

}
