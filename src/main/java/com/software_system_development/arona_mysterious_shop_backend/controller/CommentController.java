package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software_system_development.arona_mysterious_shop_backend.common.BaseResponse;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.common.ResultUtils;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.comment.CommentAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Comment;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.CommentVO;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.PageVO;
import com.software_system_development.arona_mysterious_shop_backend.service.CommentService;
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
@RequestMapping("/comment")
@Slf4j
@Tag(name = "评论接口")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 增加评论
     *
     * @param commentAddRequest 评论添加请求
     * @return {@link BaseResponse}<{@link Integer}>
     */
    @PostMapping("/add")
    @Operation(summary = "增加评论")
    public BaseResponse<Integer> addComment(@RequestBody CommentAddRequest commentAddRequest, HttpServletRequest request) {
        if (commentAddRequest == null || request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = commentService.addComment(commentAddRequest,request);
        return ResultUtils.success(result);
    }

    /**
     * 删除评论
     *
     * @param commentId 评论ID
     * @param request HttpServletRequest
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除评论")
    public BaseResponse<Boolean> deleteComment(@RequestParam Integer commentId, HttpServletRequest request) {
        if (commentId == null || commentId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }
        boolean result = commentService.deleteComment(commentId, request);
        return ResultUtils.success(result);
    }


    /**
     * 查询某商品下的所有评论
     *
     * @param productId 商品ID
     * @return {@link BaseResponse}<{@link List}<{@link CommentVO}>>
     */
    @GetMapping("/list")
    @Operation(summary = "分页查询指定商品下的所有评论")
    public BaseResponse<PageVO<CommentVO>> listComments(@RequestParam int productId,
                                                       @RequestParam(defaultValue = "1") long current,
                                                       @RequestParam(defaultValue = "10") long size, HttpServletRequest request) {
        if (productId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        PageVO<CommentVO> commentPage = commentService.listCommentsByProductId(productId, current, size, request);
        return ResultUtils.success(commentPage);
    }
}
