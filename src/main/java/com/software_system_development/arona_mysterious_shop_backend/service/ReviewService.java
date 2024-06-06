package com.software_system_development.arona_mysterious_shop_backend.service;

import com.software_system_development.arona_mysterious_shop_backend.model.dto.review.ReviewAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.review.ReviewDeleteRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Review;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author 29967
* @description 针对表【review】的数据库操作Service
* @createDate 2024-06-05 12:28:45
*/
public interface ReviewService extends IService<Review> {

    int addReview(ReviewAddRequest reviewAddRequest);

    boolean deleteReview(ReviewDeleteRequest reviewDeleteRequest, HttpServletRequest request);

    List<Review> getReviewsByProductId(Integer productId);
}