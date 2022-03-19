package com.lemon.service;

import com.lemon.vo.Result;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    Result uploadImage(MultipartFile imageFile);
}
