package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.mapper.CommentMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.comment.CommentAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Comment;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Product;
import com.software_system_development.arona_mysterious_shop_backend.model.enums.UserRoleEnum;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.CommentVO;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.PageVO;
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
    @Resource
    CommentMapper commentMapper;

    @Override
    public int addComment(CommentAddRequest commentAddRequest, HttpServletRequest request) {
        if (commentAddRequest.getCommentContent() == null || commentAddRequest.getCommentProductId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        Product product = productService.getById(commentAddRequest.getCommentProductId());
        if (product == null || product.getIsDelete() == 1) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "指定的商品ID不存在或已被删除");
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentAddRequest, comment);
        comment.setCommentUserId(userService.getUserVO(request).getUserId());
        comment.setCommentCreateDate(new Date());
        boolean saveResult = this.save(comment);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "添加评论失败");
        }
        return comment.getCommentId();
    }

    @Override
    public boolean deleteComment(Integer commentId, HttpServletRequest request) {
        // 获取当前登录用户信息
        UserVO loginUser = userService.getUserVO(request);
        // 根据评论ID查询评论信息
        Comment comment = this.getById(commentId);
        if (comment == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "评论不存在");
        }
        // 检查当前登录用户是否为评论的所有者或管理员
        if (!loginUser.getUserId().equals(comment.getCommentUserId()) && !loginUser.getUserRole().equals(UserRoleEnum.ADMIN.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无删除权限");
        }
        // 删除评论
        return this.removeById(commentId);
    }

    @Override
    public PageVO<CommentVO> listCommentsByProductId(int productId, long current, long size, HttpServletRequest request) {
        Page<Comment> page = new Page<>(current, size);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("commentProductId", productId);
        List<Comment> comments = commentMapper.selectPage(page, queryWrapper).getRecords();
        List<CommentVO> commentVOS = convertFromComments(comments, request);
        return new PageVO<>(commentVOS, page.getTotal());
    }

    private List<CommentVO> convertFromComments(List<Comment> comments, HttpServletRequest request) {
        List<CommentVO> commentVOS = null;
        if (comments != null && !comments.isEmpty()) {
            commentVOS = new java.util.ArrayList<>();
        }
        for (Comment comment : comments) {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);
            commentVO.setUserName(userService.getUserVO(request).getUserName());
            assert commentVOS != null;
            commentVOS.add(commentVO);
        }
        return commentVOS;
    }

}




