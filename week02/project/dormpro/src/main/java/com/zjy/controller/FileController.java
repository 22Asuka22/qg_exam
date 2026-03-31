package com.zjy.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class FileController {

    private final String path = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";

    @PostMapping("/upload")
    public Map<String, Object> uploadImg(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();

        try {
            if (file.isEmpty()) {
                throw new RuntimeException("文件为空");
            }

            String filename = file.getOriginalFilename();
            if (filename == null) {
                throw new RuntimeException("不是图片");
            }

            if (!filename.endsWith(".jpg") && !filename.endsWith(".png") && !filename.endsWith(".jpeg")) {
                throw new RuntimeException("不支持此格式");
            }

            File saveDir = new File(path);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            File destFile = new File(path + filename);
            file.transferTo(destFile);

            String imgUrl = "http://localhost:8080/uploads/" + filename;
            result.put("imgUrl", imgUrl);
            return result;

        } catch (Exception e) {
            throw new RuntimeException("上传失败："+e.getMessage());
        }
    }
}
