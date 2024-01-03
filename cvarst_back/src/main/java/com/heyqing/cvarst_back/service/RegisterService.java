package com.heyqing.cvarst_back.service;

import com.heyqing.cvarst_back.vo.Result;
import com.heyqing.cvarst_back.vo.params.LoginParam;
import com.heyqing.cvarst_back.vo.params.RegisterParam;

/**
 * ClassName:RegisterService
 * Package:com.heyqing.cvarst_back.service
 * Description:
 *
 * @Date:2023/11/11
 * @Author:Heyqing
 */
public interface RegisterService {
    /**
     * 注册用户
     * @param loginParam
     * @param openid
     * @return
     */
    Result register(LoginParam loginParam,String openid);
}
