package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Category;
import com.software_system_development.arona_mysterious_shop_backend.service.CategoryService;
import com.software_system_development.arona_mysterious_shop_backend.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author 29967
* @description 针对表【category】的数据库操作Service实现
* @createDate 2024-06-05 12:28:45
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

}




