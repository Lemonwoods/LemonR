package com.lemon.service.impl;

import com.lemon.service.UploadService;
import com.lemon.vo.ErrorCode;
import com.lemon.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;


@Service
public class UploadServiceImpl implements UploadService {
    final String rootFolderPath = "D:\\media\\test\\";
    final String responsePrefix = "http://localhost:8888/image/";


    @Override
    public Result uploadImage(MultipartFile imageFile) {
        //判断文件夹是否存在,不存在则创建
        File file=new File(rootFolderPath);

        if(!file.exists()){
            file.mkdirs();
        }
        String postFix = StringUtils.substringAfterLast(imageFile.getOriginalFilename(), ".");;//获取原始图片的扩展名
        String newFileName = UUID.randomUUID()+"."+postFix; // 新的文件名
        String newFilePath= rootFolderPath +newFileName; // 新文件的路径

        try {
            imageFile.transferTo(new File(newFilePath));  // 将传来的文件写入新建的文件
            return Result.succeed(responsePrefix+newFileName);
        }catch (Exception e ) {
            return Result.fail(ErrorCode.UPLOAD_ERROR.getCode(), ErrorCode.UPLOAD_ERROR.getMsg());
        }
    }
}
