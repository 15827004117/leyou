package com.leyou.exception;

import com.leyou.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ly异常
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LyException extends RuntimeException {

    private ExceptionEnum exceptionEnum;

}
