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
    /**
     * SPU详情不存在
     */
    SPU_DETAIL_NOT_FOUND(404, "SPU详情不存在"),
    /**
     *商品SKU不存在
     */
    GOODS_SKU_NOT_FOUND(404, "商品SKU不存在"),
    /**
     * 商品不存在
     */
    GOODS_NOT_FOUND(404, "商品不存在"),
    /**
     *规格参数不存在
     */
    GROUP_PARAM_NOT_FOUND(404, "规格参数不存在"),
    /**
     *商品规格添加失败
     */
    SPEC_GROUP_CREATE_FAILED(500, "商品规格添加失败"),
    /**
     *ID参数错误
     */
    INVALID_PARAM(500,"ID参数错误"),
    /**
     * 删除商品规格租失败
     */
    DELETE_SPEC_GROUP_FAILED(500,"删除商品规格组失败"),
    /**
     *修改商品规格租失败
     */
    UPDATE_SPEC_GROUP_FAILED(500,"修改商品规格租失败"),
    /**
     *新增商品规格租失败
     */
    SPEC_PARAM_CREATE_FAILED(500,"新增商品规格租失败"),
    /**
     *"修改商品规格参数失败
     */
    UPDATE_SPEC_PARAM_FAILED(500,"修改商品规格参数失败"),
    /**
     *删除商品规格参数失败
     */
    DELETE_SPEC_PARAM_FAILED(500,"删除商品规格参数失败"),
    /**
     *规格组不存在
     */
    GROUP_NOT_FOUND(404,"规格组不存在"),
    /**
     *品牌不存在
     */
    BRAND_NOT_FOUND(404, "品牌不存在"),
    /**
     *spu不存在
     */
    SPU_NOT_FOUND(404,"spu不存在"),
    /**
     *用户请求参数错误
     */
    INVALID_USER_DATA_TYPE_ERROR(400, "用户请求参数错误"),
    /**
     * 用户短信验证码错误
     */
    USER_CHECK_CODE_ERROR(400,"用户短信验证码错误"),
    /**
     * 用户不存在
     */
    INVALID_USERNAME_PASSWORD(400,"用户不存在"),
    /**
     *用户凭证生成失败
     */
    CREATE_TOKEN_ERROR(500,"用户凭证生成失败"),
    /**
     *验证用户信息失败
     */
    INTERNAL_SERVER_ERROR(500,"验证用户信息失败"),
    /**
     * 未授权
     */
    UNAUTHORIZED(403,"未授权"),
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
