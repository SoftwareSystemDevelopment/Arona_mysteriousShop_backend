package com.software_system_development.arona_mysterious_shop_backend.model.dto.file;

import lombok.Data;

@Data
public class Base64UploadFileRequest {
    private String fileBase64;

    private String biz;
}
