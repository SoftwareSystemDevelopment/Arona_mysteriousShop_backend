package com.software_system_development.arona_mysterious_shop_backend.service;

import com.software_system_development.arona_mysterious_shop_backend.model.dto.comment.CommentAddRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Comment;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author 29967
* @description 针对表【comment】的数据库操作Service
* @createDate 2024-06-05 12:28:45
*/
public interface CommentService extends IService<Comment> {

    /**
     * 添加评论
     * @param commentAddRequest
     * @param request
     * @return
     */
    int addComment(CommentAddRequest commentAddRequest, HttpServletRequest request);

    /**
     * 删除评论
     * @param commentId
     * @param request
     * @return
     */
    boolean deleteComment(Integer commentId, HttpServletRequest request);


    /**
     * 查询某商品下的所有评论
     * @param productId
     * @return
     */
    List<Comment> getCommentsByProductId(Integer productId);
}