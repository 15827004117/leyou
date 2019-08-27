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
    BRAND_NOT_FOND(404, "品牌不存在"),
    CATEGORY_BRAND_SAVE_ERROR(500, "商品、品牌中间表新增数据失败"),
    UPLOAD_FILE_ERROR(500, "上传文件失败"),
    INVALID_FILE_ERROR(400, "无效文件类型"),
    SPEC_GROUP_NOT_FOND(404, "规格组未查询到"),
    GOODS_NOT_FOND(404, "商品不存在"),
    CATEGORY_NOT_FOND(404, "商品分类不存在"),
    ;

    private int code;
    private String msg;

}
