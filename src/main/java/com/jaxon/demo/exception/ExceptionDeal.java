package com.jaxon.demo.exception;

import com.jaxon.demo.entity.ApiResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionDeal {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResult userNull(Exception e){
        if(e instanceof  UserException){
            return ApiResult.fail(((UserException) e).code,e.getMessage());
        }else{
            return ApiResult.fail(-1,e.getMessage());
        }
    }

}
