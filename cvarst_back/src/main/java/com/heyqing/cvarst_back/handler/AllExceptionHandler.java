package com.heyqing.cvarst_back.handler;

import com.heyqing.cvarst_back.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName:AllExceptionHandler
 * Package:com.heyqing.cvarst_back.handler
 * Description:
 *
 * @Date:2023/11/11
 * @Author:Heyqing
 */

//对加了controller注解的方法进行拦截处理AOP实现
@ControllerAdvice
@ResponseBody//返回json数据
public class AllExceptionHandler {
    //进行异常处理，处理Exception.class的异常
    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex){
        ex.printStackTrace();
        return Result.fail(-999,"系统异常");
    }
}

