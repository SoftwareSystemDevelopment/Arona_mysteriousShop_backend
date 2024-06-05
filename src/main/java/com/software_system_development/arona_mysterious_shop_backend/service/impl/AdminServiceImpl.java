package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Admin;
import com.software_system_development.arona_mysterious_shop_backend.service.AdminService;
import com.software_system_development.arona_mysterious_shop_backend.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author 29967
* @description 针对表【admin】的数据库操作Service实现
* @createDate 2024-06-05 12:28:45
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

}




