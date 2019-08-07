package com.leyou.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 异常枚举类
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  ExceptionEnum {

    SUCCESS(200,"成功"),
    BRAND_SAVE_ERROR(500,"新增品牌失败"),
    CATEGORY_BRAND_SAVE_ERROR(500, "商品、品牌中间表新增数据失败"),
    UPLOAD_FILE_ERROR(500, "上传文件失败"),
    INVALID_FILE_ERROR(400, "无效文件类型")
    ;

    private int code;
    private String msg;

}
