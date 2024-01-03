package com.heyqing.cvarst_back.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.heyqing.cvarst_back.dao.mapper.SysUserMapper;
import com.heyqing.cvarst_back.dao.pojo.SysUser;
import com.heyqing.cvarst_back.service.SysUserService;
import com.heyqing.cvarst_back.utils.JWTUtils;
import com.heyqing.cvarst_back.vo.ErrorCode;
import com.heyqing.cvarst_back.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * ClassName:SysUserServiceImpl
 * Package:com.heyqing.cvarst_back.service.impl
 * Description:
 *
 * @Date:2023/11/11
 * @Author:Heyqing
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查找用户
     *
     * @param openid
     * @return
     */
    @Override
    public SysUser findUser(String openid) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getOpenid,openid);
        queryWrapper.select(SysUser::getId,SysUser::getOpenid,SysUser::getToken);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }


    /**
     * 保存用户
     *
     * @param sysUser
     */
    @Override
    public void save(SysUser sysUser) {
        //保存后Id自动生成
        //默认生成的Id是分布式Id 雪花算法
        this.sysUserMapper.insert(sysUser);
    }

    /**
     * 更新token
     *
     * @param sysUser
     */
    @Override
    public void updateToken(SysUser sysUser) {
        this.sysUserMapper.updateById(sysUser);
    }

    /**
     * 根据token查询用户
     *
     * @param token
     * @return
     */
    @Override
    public SysUser findUserByToken(String token) {
        /**
         * 1.token合法校验
         *      是否为空 解析是否成功 Redis是否存在
         * 2.如果校验失败 返回错误
         * 3.如果校验成功 返回结果  sysUser
         */
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null){
            return null;
        }
        String userJson = (String) redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }
}
