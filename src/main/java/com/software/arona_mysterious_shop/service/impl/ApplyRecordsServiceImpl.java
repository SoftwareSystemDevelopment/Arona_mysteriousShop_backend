package com.software.arona_mysterious_shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software.arona_mysterious_shop.common.ErrorCode;
import com.software.arona_mysterious_shop.exception.BusinessException;
import com.software.arona_mysterious_shop.exception.ThrowUtils;
import com.software.arona_mysterious_shop.model.entity.ApplyRecords;
import com.software.arona_mysterious_shop.service.ApplyRecordsService;
import com.software.arona_mysterious_shop.mapper.ApplyRecordsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author 29967
* @description 针对表【apply_records(申请记录表)】的数据库操作Service实现
* @createDate 2024-06-03 14:34:50
*/
@Service
public class ApplyRecordsServiceImpl extends ServiceImpl<ApplyRecordsMapper, ApplyRecords>
        implements ApplyRecordsService{

    @Override
    public void validApplyRecords(ApplyRecords applyRecords, boolean add) {
        if (applyRecords == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }
}

