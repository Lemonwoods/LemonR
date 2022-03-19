package com.lemon.controller;

import com.lemon.service.UploadService;
import com.lemon.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("upload")
public class UploadController {
    @Resource
    UploadService uploadService;

    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile imageFile){
        return uploadService.uploadImage(imageFile);
    }
}
