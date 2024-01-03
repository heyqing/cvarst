package com.heyqing.cvarst_back.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.heyqing.cvarst_back.dao.mapper.*;
import com.heyqing.cvarst_back.dao.pojo.*;
import com.heyqing.cvarst_back.service.PersonService;
import com.heyqing.cvarst_back.service.SysUserService;
import com.heyqing.cvarst_back.utils.GetOpenidUtils;
import com.heyqing.cvarst_back.vo.ArticleVo;
import com.heyqing.cvarst_back.vo.ErrorCode;
import com.heyqing.cvarst_back.vo.Result;
import com.heyqing.cvarst_back.vo.params.PersonPageParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:PersonServiceImpl
 * Package:com.heyqing.cvarst_back.service.impl
 * Description:
 *
 * @Date:2023/11/21
 * @Author:Heyqing
 */
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PersonLikeMapper personLikeMapper;
    @Autowired
    private PersonPhotoDataMapper personPhotoDataMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private FeedbackMapper feedbackMapper;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询我的喜欢
     *
     * @param personPageParam
     * @return
     */
    @Override
    public Result findArticleLikeById(PersonPageParam personPageParam) {
        /**
         * 1.查询出当前用户id
         * 2.根据用户的id查询出所有文章
         * 3.返回文章avatar，title，bodyId
         */
        String token = personPageParam.getToken();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getToken,token);
        queryWrapper.select(SysUser::getId);
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        Long sysUserId = sysUser.getId();
        //拿到用户id，去like表中进行查询
        LambdaQueryWrapper<ArticleLike> likeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        likeLambdaQueryWrapper.eq(ArticleLike::getSysId,sysUserId);
        likeLambdaQueryWrapper.select(ArticleLike::getArticleId);
        List<ArticleLike> articleLikes = personLikeMapper.selectList(likeLambdaQueryWrapper);
        //从articleLikes中得到ArticleId去article表中查询
        List<Article> articleList = new ArrayList<>();
        for (ArticleLike articleLike : articleLikes) {
            articleList.add(articleMapper.selectById(articleLike.getArticleId()));
        }
        return Result.success(copyList(articleList));
    }

    /**
     * 存入用户反馈意见
     *
     * @param feedbackText
     * @return
     */
    @Override
    public Result insertText(String feedbackText) {
        Feedback feedback = new Feedback();
        feedback.setFeedbackText(feedbackText);
        int insert = feedbackMapper.insert(feedback);
        return Result.success(insert);
    }

    /**
     * 查找所有收藏照片
     *
     * @param token
     * @return
     */
    @Override
    public Result findAllPhotoByToken(String token) {
        SysUser userByToken = sysUserService.findUserByToken(token);
        List<String> photoPathList = new ArrayList<>();
        if (userByToken == null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }
        Long userId = userByToken.getId();
        LambdaQueryWrapper<PhotoData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PhotoData::getSysId,userId);
        queryWrapper.select(PhotoData::getId,PhotoData::getPhotoPath,PhotoData::getSysId);
        List<PhotoData> photoDataList = personPhotoDataMapper.selectList(queryWrapper);
        for (PhotoData photoData : photoDataList) {
            photoPathList.add(photoData.getPhotoPath());
        }
        return Result.success(photoPathList);
    }

    /**
     * 删除我的喜欢
     *
     * @param articleId
     * @return
     */
    @Override
    public Result deleteLikeByArticleId(Long articleId) {
        LambdaQueryWrapper<ArticleLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleLike::getArticleId,articleId);
        personLikeMapper.delete(queryWrapper);
        return Result.success("删除成功");
    }

    /**
     * 删除图片
     *
     * @param photoPath
     * @return
     */
    @Override
    public Result deletePhotoByPhotoPath(String photoPath) {
        LambdaQueryWrapper<PhotoData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PhotoData::getPhotoPath,photoPath);
        personPhotoDataMapper.delete(queryWrapper);
        return Result.success("删除成功");
    }

    private ArticleVo copy(Article article) {
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        return articleVo;
    }

    private List<ArticleVo> copyList(List<Article> records) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : records) {
            ArticleVo articleVo = copy(article);
            articleVoList.add(articleVo);
        }
        return articleVoList;
    }
}
