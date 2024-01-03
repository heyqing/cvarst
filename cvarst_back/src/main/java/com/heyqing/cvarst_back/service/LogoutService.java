package com.heyqing.cvarst_back.service;

import com.heyqing.cvarst_back.dao.pojo.SysUser;
import com.heyqing.cvarst_back.vo.Result;
import com.heyqing.cvarst_back.vo.params.LogoutParam;

/**
 * ClassName:LogoutService
 * Package:com.heyqing.cvarst_back.service
 * Description:
 *
 * @Date:2023/11/11
 * @Author:Heyqing
 */
public interface LogoutService {

    /**
     * 退出登录
     * @param logoutParam
     * @return
     */
    Result logout(LogoutParam logoutParam);
}
