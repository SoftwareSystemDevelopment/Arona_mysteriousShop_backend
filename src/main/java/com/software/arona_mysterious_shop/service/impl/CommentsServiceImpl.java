package com.software.arona_mysterious_shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software.arona_mysterious_shop.model.entity.Comments;
import com.software.arona_mysterious_shop.service.CommentsService;
import com.software.arona_mysterious_shop.mapper.CommentsMapper;
import org.springframework.stereotype.Service;

/**
* @author 29967
* @description 针对表【comments(评论表)】的数据库操作Service实现
* @createDate 2024-06-03 14:34:50
*/
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments>
    implements CommentsService {

}




