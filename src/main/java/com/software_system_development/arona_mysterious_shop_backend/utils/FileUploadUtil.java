package com.software_system_development.arona_mysterious_shop_backend.utils;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUploadUtil {

    // 上传文件的保存路径
    private static final String UPLOAD_DIR = "./src/main/resources/static/assets/";

    public static String uploadImageAndGetUrl(MultipartFile file) {
        // 检查文件是否为空
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传的文件为空");
        }

        try {
            // 获取文件名
            String originalFileName = file.getOriginalFilename();

            // 生成唯一的文件名
            String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

            // 目标存储路径
            Path targetLocation = Paths.get(UPLOAD_DIR + fileName);

            // 如果目录不存在，则创建
            Files.createDirectories(targetLocation.getParent());

            // 将文件保存到目标路径
            Files.copy(file.getInputStream(), targetLocation);

            // 返回文件的访问路径
            return "/assets/" + fileName; // 这里假设上传到了名为 "assets" 的文件夹下
        } catch (IOException ex) {
            throw new RuntimeException("无法上传文件: " + ex.getMessage());
        }
    }
}
