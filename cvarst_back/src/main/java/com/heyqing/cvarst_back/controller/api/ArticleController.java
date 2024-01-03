package com.heyqing.cvarst_back.controller.api;

import com.heyqing.cvarst_back.common.aop.LogAnnotation;
import com.heyqing.cvarst_back.service.ArticleService;
import com.heyqing.cvarst_back.vo.Result;
import com.heyqing.cvarst_back.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName:ArticleController
 * Package:com.heyqing.cvarst_back.controller.api
 * Description:
 *
 * @Date:2023/11/10
 * @Author:Heyqing
 */
@RestController
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 首页   文章列表
     *
     * @param pageParams
     * @return
     */
    @LogAnnotation(module = "文章",operator = "获取文章列表")
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }

    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }
    /**
     * 首页   最新文章
     *
     * @return
     */
    @LogAnnotation(module = "文章",operator = "获取最新文章")
    @PostMapping("new")
    public Result newArticle(@RequestParam int newStart) {
        int limit = 5;
        return articleService.newArticle(newStart,limit);
    }

    /**
     * 首页   最热文章
     *
     * @return
     */
    @LogAnnotation(module = "文章",operator = "获取最热文章")
    @PostMapping("hot")
    public Result hotArticle(@RequestParam int hotStart){
        int limit = 5;
        return articleService.hotArticle(hotStart,limit);
    }

    /**
     * 文章详情
     * @param articleId
     * @return
     */
    @LogAnnotation(module = "文章",operator = "获取文章详情")
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId) {
        return articleService.findArticleById(articleId);
    }
    /**
     * 搜索文章
     * 搜索建议
     * 为点击搜索按钮
     * @param kw
     * @return
     */
    @GetMapping("qsearch")
    public Result searchArticleByQkw(@RequestParam String kw){
        return articleService.findArticleByQkw(kw);
    }
    /**
     * 文章自动导入
     * 在common目录下
     */
}
