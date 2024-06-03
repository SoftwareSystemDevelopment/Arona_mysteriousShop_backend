package com.software.arona_mysterious_shop.service;

import com.software.arona_mysterious_shop.model.entity.ApplyRecords;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 29967
* @description 针对表【apply_records(申请记录表)】的数据库操作Service
* @createDate 2024-06-03 14:34:50
*/
public interface ApplyRecordsService extends IService<ApplyRecords> {

    void validApplyRecords(ApplyRecords applyRecords, boolean add);

}
