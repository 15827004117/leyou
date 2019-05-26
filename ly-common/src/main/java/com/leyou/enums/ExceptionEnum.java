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

    SUCCESS(200,"成功");

    private int code;
    private String msg;


}
