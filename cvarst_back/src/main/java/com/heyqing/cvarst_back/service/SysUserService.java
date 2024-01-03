package com.heyqing.cvarst_back.service;

import com.heyqing.cvarst_back.dao.pojo.SysUser;
import com.heyqing.cvarst_back.vo.Result;

/**
 * ClassName:SysUserService
 * Package:com.heyqing.cvarst_back.service
 * Description:
 *
 * @Date:2023/11/11
 * @Author:Heyqing
 */
public interface SysUserService {
    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUser sysUser);

    /**
     * 查找用户
     * @param openid
     * @return
     */
    SysUser findUser(String openid);

    /**
     * 更新token
     * @param sysUser
     */
    void updateToken(SysUser sysUser);

    /**
     * 根据token查询用户
     * @param token
     * @return
     */
    SysUser findUserByToken(String token);
}
