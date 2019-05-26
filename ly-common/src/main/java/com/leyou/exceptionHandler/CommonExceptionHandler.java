package com.leyou.exceptionHandler;

import com.leyou.exception.LyException;
import com.leyou.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 统一异常处理类
 */
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(LyException.class)
    public ResponseEntity<ExceptionResult> exceptionHandler(LyException e) {
        return ResponseEntity.status(e.getExceptionEnum().getCode()).body(new ExceptionResult(e.getExceptionEnum()));
    }
}
