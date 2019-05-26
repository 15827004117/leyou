package com.leyou.vo;

import com.leyou.enums.ExceptionEnum;
import lombok.Data;

/**
 * 异常处理结果
 */
@Data
public class ExceptionResult {

    private int status;
    private String message;
    private Long timestamp;

    public ExceptionResult(ExceptionEnum em) {
        this.status = em.getCode();
        this.message = em.getMsg();
        this.timestamp = System.currentTimeMillis();
    }
}
