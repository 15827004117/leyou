package com.leyou.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 异常枚举类
 * @author lijing
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  ExceptionEnum {

    /**
     * 成功
     */
    SUCCESS(200,"成功"),
    /**
     * 新增品牌失败
     */
    BRAND_SAVE_ERROR(500,"新增品牌失败"),
    /**
     * 品牌不存在
     */
    BRAND_NOT_FOND(404, "品牌不存在"),
    /**
     * 商品、品牌中间表新增数据失败
     */
    CATEGORY_BRAND_SAVE_ERROR(500, "商品、品牌中间表新增数据失败"),
    /**
     * 上传文件失败
     */
    UPLOAD_FILE_ERROR(500, "上传文件失败"),
    /**
     * 无效文件类型
     */
    INVALID_FILE_ERROR(400, "无效文件类型"),
    /**
     * 规格组未查询到
     */
    SPEC_GROUP_NOT_FOND(404, "规格组未查询到"),
    /**
     * 商品不存在
     */
    GOODS_NOT_FOND(404, "商品不存在"),
    /**
     * 商品分类不存在
     */
    CATEGORY_NOT_FOND(404, "商品分类不存在"),
    ;

    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String msg;

}
