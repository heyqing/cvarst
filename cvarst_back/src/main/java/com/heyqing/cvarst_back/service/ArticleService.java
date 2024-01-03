package com.heyqing.cvarst_back.service;

import com.heyqing.cvarst_back.vo.Result;
import com.heyqing.cvarst_back.vo.params.PageParams;

import java.util.List;

/**
 * ClassName:ArticleService
 * Package:com.heyqing.cvarst_back.service.impl
 * Description:
 *
 * @Date:2023/11/10
 * @Author:Heyqing
 */
public interface ArticleService {
    /**
     * 分页查询 文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticle(int newStart,int limit);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int hotStart ,int limit);

    /**
     * 查询文章详情
     * @param articleId
     * @return
     */
    Result findArticleById(Long articleId);

    Result listArchives();

    /**
     * 搜索建议
     * @param kw
     * @return
     */
    Result findArticleByQkw(String kw);

    /**
     * 根据articleId查询
     * @param articleIdList
     * @return
     */
    Result findArticleByIdList(List<Long> articleIdList);
}
