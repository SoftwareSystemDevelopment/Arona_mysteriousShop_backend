package com.software.arona_mysterious_shop.manager;

import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Cos 操作测试
 *
 */
@SpringBootTest
class CosManagerTest {

    @Resource
    private TencentCosManager cosManager;

    @Test
    void putObject() {
        cosManager.putObject("test", "src/main/resources/biz/rule.json");
    }
}