package com.heyqing.cvarst_back.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.heyqing.cvarst_back.dao.pojo.SysUser;
import com.heyqing.cvarst_back.service.RegisterService;
import com.heyqing.cvarst_back.service.SysUserService;
import com.heyqing.cvarst_back.utils.JWTUtils;
import com.heyqing.cvarst_back.vo.ErrorCode;
import com.heyqing.cvarst_back.vo.Result;
import com.heyqing.cvarst_back.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * ClassName:RegisterServiceImpl
 * Package:com.heyqing.cvarst_back.service.impl
 * Description:
 *
 * @Date:2023/11/11
 * @Author:Heyqing
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 注册用户
     *
     * @param loginParam
     * @return
     */
    @Override
    public Result register(LoginParam loginParam,String openid) {
        /**
         * 1.判断参数是否合法
         * 2.判断账户是否存在
         *      存在 返回账号已经被注册
         *      不存在 注册账户
         * 3.生成token
         * 4.存入Redis并返回
         * 5.注意
         *      加上事务 一旦中间的任何过程出现问题 注册的用户需要回滚
         */
        String code = loginParam.getCode();
        String encryptedData = loginParam.getEncryptedData();
        String iv = loginParam.getIv();
        String rawData = loginParam.getRawData();
        String signature = loginParam.getSignature();
        SysUser sysUser = new SysUser();
        sysUser.setCode(code);
        sysUser.setEncryptedData(encryptedData);
        sysUser.setIv(iv);
        sysUser.setRawData(rawData);
        sysUser.setSignature(signature);
        sysUser.setOpenid(openid);
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        this.sysUserService.save(sysUser);


        return Result.success(sysUser);
    }
}
