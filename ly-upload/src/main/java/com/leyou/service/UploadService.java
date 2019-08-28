package com.leyou.service;

import com.leyou.controller.UploadController;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 图片上传
 * @author lijing
 */
@Slf4j
@Service
public class UploadService {

    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    /** 支持的文件类型 */
    private static final List<String> SUFFIXES = Arrays.asList("image/png", "image/jpeg");

    /** 图片上传 */
    public String upload(MultipartFile file) {
        try {
            // 文件类型效验
            if(!SUFFIXES.contains(file.getContentType())) {
                throw new LyException(ExceptionEnum.INVALID_FILE_ERROR);
            }
            // 文件内容效验
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image == null) {
                throw new LyException(ExceptionEnum.INVALID_FILE_ERROR);
            }
            // 保存的路径
            File path = new File("D:\\upload", Objects.requireNonNull(file.getOriginalFilename()));
            // 保存至本地磁盘
            file.transferTo(path);
            // 返回路径
            String url = "http://image.leyou.com/upload/" + file.getOriginalFilename();
            return url;
        } catch (IOException e) {
            // 失败
            log.error("上传文件失败", e);
            throw new LyException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }
    }
}
