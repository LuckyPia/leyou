package com.leyou.common.advice;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.RespBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(LyException.class)
    public ResponseEntity<RespBean> handleException(LyException e){
        ExceptionEnum em = e.getExceptionEnum();
        return ResponseEntity.status(HttpStatus.OK).body(RespBean.status(em.getCode(),em.getMsg()));
    }
}
