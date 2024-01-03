package com.heyqing.cvarst_back.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName:HttpContexUtils
 * Package:com.heyqing.cvarst_back.utils
 * Description:
 *
 * @Date:2023/11/12
 * @Author:Heyqing
 */
public class HttpContextUtils {
    public static HttpServletRequest getHttpServletRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
