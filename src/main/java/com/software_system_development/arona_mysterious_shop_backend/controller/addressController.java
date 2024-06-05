package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.software_system_development.arona_mysterious_shop_backend.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
@Slf4j
@Tag(name = "地址接口")
public class addressController {
    @Resource
    AddressService addressService;
}
