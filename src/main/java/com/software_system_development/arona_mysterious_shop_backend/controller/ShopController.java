package com.software_system_development.arona_mysterious_shop_backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
@Slf4j
@Tag(name = "店铺接口")
public class ShopController {

    //TODO
    //1. 增加店铺接口
    //2. 更改店铺信息接口
    //3. 展示店铺所有商品信息接口
}
