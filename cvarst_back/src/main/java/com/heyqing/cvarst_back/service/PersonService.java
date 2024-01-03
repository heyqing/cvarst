package com.heyqing.cvarst_back.service;

import com.heyqing.cvarst_back.vo.Result;
import com.heyqing.cvarst_back.vo.params.PersonPageParam;

/**
 * ClassName:PersonService
 * Package:com.heyqing.cvarst_back.service
 * Description:
 *
 * @Date:2023/11/21
 * @Author:Heyqing
 */
public interface PersonService {
    /**
     * 查询我的喜欢
     * @param personPageParam
     * @return
     */
    Result findArticleLikeById(PersonPageParam personPageParam);

    /**
     * 存入用户反馈意见
     * @param feedbackText
     * @return
     */
    Result insertText(String feedbackText);

    /**
     * 查找所有收藏照片
     * @param token
     * @return
     */
    Result findAllPhotoByToken(String token);

    /**
     * 删除我的喜欢
     * @param articleId
     * @return
     */
    Result deleteLikeByArticleId(Long articleId);

    /**
     * 删除图片
     * @param photoPath
     * @return
     */
    Result deletePhotoByPhotoPath(String photoPath);
}
