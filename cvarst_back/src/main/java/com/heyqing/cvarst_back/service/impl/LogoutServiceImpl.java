package com.heyqing.cvarst_back.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heyqing.cvarst_back.dao.mapper.PersonLikeMapper;
import com.heyqing.cvarst_back.dao.mapper.PersonPhotoDataMapper;
import com.heyqing.cvarst_back.dao.mapper.SysUserMapper;
import com.heyqing.cvarst_back.dao.pojo.ArticleLike;
import com.heyqing.cvarst_back.dao.pojo.PhotoData;
import com.heyqing.cvarst_back.dao.pojo.SysUser;
import com.heyqing.cvarst_back.service.LogoutService;
import com.heyqing.cvarst_back.service.SysUserService;
import com.heyqing.cvarst_back.utils.GetOpenidUtils;
import com.heyqing.cvarst_back.vo.Result;
import com.heyqing.cvarst_back.vo.params.LogoutParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName:LogoutServiceImpl
 * Package:com.heyqing.cvarst_back.service.impl
 * Description:
 *
 * @Date:2023/11/11
 * @Author:Heyqing
 */
@Service
public class LogoutServiceImpl implements LogoutService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PersonLikeMapper personLikeMapper;
    @Autowired
    private PersonPhotoDataMapper personPhotoDataMapper;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 退出登录
     *
     * @param logoutParam
     * @return
     */
    @Override
    public Result logout(LogoutParam logoutParam) {

        //文章id
        List<Long> articleIdList = logoutParam.getArticleList();
        //图片
        List<String> photoList = logoutParam.getPhotoList();
        if (articleIdList == null && photoList == null){
            return null;
        }
        //code
        String token = logoutParam.getToken();
        //根据token查找用户
        SysUser sysUser = sysUserService.findUserByToken(token);
        if (sysUser != null){
            Long userId = sysUser.getId();
            //分别去like表与data表插入
            if (articleIdList.size() != 0) {
                for (Long articleId : articleIdList) {
                    ArticleLike articleLike = new ArticleLike();
                    articleLike.setArticleId(articleId);
                    articleLike.setSysId(userId);
                    personLikeMapper.insert(articleLike);
                }
            }
            if (photoList.size() != 0) {
                for (String photoPath : photoList) {
                    PhotoData photoData = new PhotoData();
                    photoData.setPhotoPath(photoPath);
                    photoData.setSysId(userId);
                    personPhotoDataMapper.insert(photoData);
                }
            }
        }
        return Result.success(null);
    }
}
