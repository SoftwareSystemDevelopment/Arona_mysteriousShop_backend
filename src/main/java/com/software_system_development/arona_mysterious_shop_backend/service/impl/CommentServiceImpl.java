package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.mapper.CommentMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.comment.CommentAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.comment.CommentDeleteRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Product;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Comment;
import com.software_system_development.arona_mysterious_shop_backend.model.enums.UserRoleEnum;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import com.software_system_development.arona_mysterious_shop_backend.service.ProductService;
import com.software_system_development.arona_mysterious_shop_backend.service.CommentService;
import com.software_system_development.arona_mysterious_shop_backend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 29967
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2024-06-05 12:28:45
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    UserService userService;
    @Resource
    ProductService productService;

    @Override
    public int addComment(CommentAddRequest commentAddRequest) {
        if (commentAddRequest == null || commentAddRequest.getCommentContent() == null || commentAddRequest.getCommentUserId() == null || commentAddRequest.getCommentProductId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        // 检查 commentUserId 是否存在
        if (userService.getById(commentAddRequest.getCommentUserId()) == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "指定的用户ID不存在");
        }

        // 检查 commentProductId 是否存在
        Product product = productService.getById(commentAddRequest.getCommentProductId());
        if (product == null || product.getIsDelete() == 1) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "指定的商品ID不存在或已被删除");
        }

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentAddRequest, comment);
        comment.setCommentCreateDate(new Date());

        boolean saveResult = this.save(comment);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "添加评论失败");
        }

        return comment.getCommentId();
    }

    @Override
    public boolean deleteComment(CommentDeleteRequest commentDeleteRequest, HttpServletRequest request) {
        if (commentDeleteRequest == null || commentDeleteRequest.getCommentId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }

        Comment comment = this.getById(commentDeleteRequest.getCommentId());
        if (comment == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "评论未找到");
        }

        // 从请求中获取当前用户信息
        UserVO loginUser = userService.getUserVO(request);
        String userRole = loginUser.getUserRole();

        // 验证用户权限
        if (!UserRoleEnum.ADMIN.getValue().equals(userRole) && !commentDeleteRequest.getCommentUserId().equals(loginUser.getUserId())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限删除");
        }

        boolean removeResult = this.removeById(commentDeleteRequest.getCommentId());
        if (!removeResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除评论失败");
        }

        return true;
    }


    @Override
    public List<Comment> getCommentsByProductId(Integer productId) {
        if (productId == null || productId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("commentProductId", productId);
        List<Comment> commentList = this.list(queryWrapper);
        if (commentList == null || commentList.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "未找到评论");
        }

        return commentList;
    }
}




