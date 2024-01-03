package com.heyqing.cvarst_back.service.impl;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.heyqing.cvarst_back.service.RegisterService;
import com.heyqing.cvarst_back.dao.pojo.SysUser;
import com.heyqing.cvarst_back.service.LoginService;
import com.heyqing.cvarst_back.service.SysUserService;
import com.heyqing.cvarst_back.utils.GetOpenidUtils;
import com.heyqing.cvarst_back.utils.JWTUtils;
import com.heyqing.cvarst_back.vo.ErrorCode;
import com.heyqing.cvarst_back.vo.Result;
import com.heyqing.cvarst_back.vo.params.LoginParam;
import com.heyqing.cvarst_back.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * ClassName:LoginServiceImpl
 * Package:com.heyqing.cvarst_back.service.impl
 * Description:
 *
 * @Date:2023/11/11
 * @Author:Heyqing
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private RegisterService registerService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 个人主页     登录
     *
     * @param loginParam
     * @return
     */
    @Override
    public Result login(LoginParam loginParam) {
        //检查是否为空
        String code = loginParam.getCode();
        String encryptedData = loginParam.getEncryptedData();
        String iv = loginParam.getIv();
        String rawData = loginParam.getRawData();
        String signature = loginParam.getSignature();
        if (StringUtils.isBlank(code) || StringUtils.isBlank(encryptedData)
                || StringUtils.isBlank(iv) || StringUtils.isBlank(rawData)
                || StringUtils.isBlank(signature)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        //参数正确  先查询用户是否存在
        //存在    生成token
        JSONObject sessionKeyOrOpenId = GetOpenidUtils.getSessionKeyOrOpenId(code);
        String openid = (String) sessionKeyOrOpenId.get("openid");

        SysUser sysUser = sysUserService.findUser(openid);
        String token = null;
        //不存在  注册用户 生成token
        if (sysUser == null) {
            sysUser = (SysUser) registerService.register(loginParam, openid).getData();
            //生成token,存入用户表.根据用户id生成
            token = JWTUtils.createToken(sysUser.getId());
            sysUser.setToken(token);
            sysUserService.updateToken(sysUser);
        }
        token = sysUser.getToken();
        if (checkToken(token) == null) {
            //生成新的token
            token = JWTUtils.createToken(sysUser.getId());
            sysUser.setToken(token);
            sysUserService.updateToken(sysUser);
        }
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUserService.updateToken(sysUser);
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 12, TimeUnit.HOURS);
        return Result.success(token);
    }

    @Override
    public Result checkToken(String token) {
        if (org.apache.commons.lang3.StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }
        String userJson = (String) redisTemplate.opsForValue().get("TOKEN_" + token);
        if (org.apache.commons.lang3.StringUtils.isBlank(userJson)) {
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return Result.success(sysUser);
    }
}
