package com.software.arona_mysterious_shop.manager;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.software.arona_mysterious_shop.model.Permission;
import org.springframework.stereotype.Service;

/**
 * 权限 配置管理
 *
 */
@Service
public class RuleConfigManager {

    private final static Permission permission;

    static {
        String json = ResourceUtil.readUtf8Str("biz/rule.json");
        permission = JSONUtil.toBean(json, Permission.class);
    }

    //获取permission
    public Permission getPermission() {
        return permission;
    }
}
