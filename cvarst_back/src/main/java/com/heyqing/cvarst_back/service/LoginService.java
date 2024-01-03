package com.heyqing.cvarst_back.service;

import com.heyqing.cvarst_back.dao.pojo.SysUser;
import com.heyqing.cvarst_back.vo.Result;
import com.heyqing.cvarst_back.vo.params.LoginParam;

/**
 * ClassName:LoginService
 * Package:com.heyqing.cvarst_back.service
 * Description:
 *
 * @Date:2023/11/11
 * @Author:Heyqing
 */
public interface LoginService {
    /**
     * 个人主页     登录
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    /**
     * 检查token合法性
     * @param token
     * @return
     */
    Result checkToken(String token);
}
