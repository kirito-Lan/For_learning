package com.kirito.management.controller;

import com.kirito.management.pojo.Result;
import com.kirito.management.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upLoadFile(MultipartFile file) throws Exception {
        String OriginalFilename = file.getOriginalFilename();
        String Filename= UUID.randomUUID() +OriginalFilename.substring(OriginalFilename.lastIndexOf("."));
        //file.transferTo(new File("D:\\桌面\\"+Filename));
        String url = AliOssUtil.uploadFile(Filename, file.getInputStream());
        return Result.success(url);
    }

}
