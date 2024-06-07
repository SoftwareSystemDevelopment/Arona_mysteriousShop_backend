package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Shop;
import generator.service.ShopService;
import generator.mapper.ShopMapper;
import org.springframework.stereotype.Service;

/**
* @author 29967
* @description 针对表【shop】的数据库操作Service实现
* @createDate 2024-06-07 10:39:04
*/
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop>
    implements ShopService{

}




