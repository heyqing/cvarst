package com.heyqing.cvarst_back.utils;

import com.heyqing.cvarst_back.dao.pojo.SysUser;

/**
 * ClassName:UserThreadLocal
 * Package:com.heyqing.cvarst_back.utils
 * Description:
 *
 * @Date:2023/11/11
 * @Author:Heyqing
 */
public class UserThreadLocal {
    private UserThreadLocal(){}

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }

    public static SysUser get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }
}
