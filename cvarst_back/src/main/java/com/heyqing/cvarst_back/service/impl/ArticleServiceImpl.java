package com.heyqing.cvarst_back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heyqing.cvarst_back.dao.dos.Archives;
import com.heyqing.cvarst_back.dao.mapper.ArticleBodyMapper;
import com.heyqing.cvarst_back.dao.mapper.ArticleMapper;
import com.heyqing.cvarst_back.dao.pojo.Article;
import com.heyqing.cvarst_back.dao.pojo.ArticleBody;
import com.heyqing.cvarst_back.service.ArticleService;
import com.heyqing.cvarst_back.service.ThreadService;
import com.heyqing.cvarst_back.vo.ArticleBodyVo;
import com.heyqing.cvarst_back.vo.ArticleVo;
import com.heyqing.cvarst_back.vo.Result;
import com.heyqing.cvarst_back.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:ArticleServiceImpl
 * Package:com.heyqing.cvarst_back.service.impl
 * Description:
 *
 * @Date:2023/11/10
 * @Author:Heyqing
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ThreadService threadService;

    /**
     * 分页查询 文章列表
     *
     * @param pageParams
     * @return
     */
    @Override
    public Result listArticle(PageParams pageParams) {
        Long total = articleMapper.allArticle();
        //获取当前页数，以及显示数量
        //Page创建分页查询条件，传递给后端进行查询
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        //通过调用articleMapper中的listArticle方法查询
        //传递page，接收后端返回的分页数据
        IPage<Article> articleIPage = articleMapper.listArticle(page, pageParams.getCategoryId(), pageParams.getTagId(), pageParams.getYear(), pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
        //需要转换成vo对象返回
        return Result.success(total, copyList(records));
    }

    /**
     * 最新文章
     *
     * @param limit
     * @param newStart
     * @return
     */
    @Override
    public Result newArticle(int newStart, int limit) {
        //查询SQL语句
        //select id ,title from wx_article order by create_data desc limit 5 offset nerStart

        //LambdaQueryWrapper是mybatis-plus中用于构建SQL查询语句
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //根据创建时间排序  DESC降序排序
        queryWrapper.orderByDesc(Article::getCreateDate);
        //拿到文章id以及title
        queryWrapper.select(Article::getId, Article::getTitle, Article::getAvatar);
        queryWrapper.last("limit " + limit + " offset " + newStart);
        //通过articleMapper进行SQL查询将其结果返回给list数组
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles));
    }

    /**
     * 最热文章
     *
     * @param limit
     * @param hotStart
     * @return
     */
    @Override
    public Result hotArticle(int hotStart, int limit) {
        //select id ,title from wx_article order by view_counts desc limit 5 offset hotStart
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle, Article::getAvatar);
        queryWrapper.last("limit " + limit + " offset " + hotStart);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles));
    }

    /**
     * 查询文章详情
     *
     * @param articleId
     * @return
     */
    @Override
    public Result findArticleById(Long articleId) {
        /**
         * 1.根据id查询文章信息
         * 2.根据bodyId和categoryId去做关联查询
         */
        Article article = this.articleMapper.selectById(articleId);
        Long bodyId = article.getBodyId();
        ArticleBody articleBody = this.articleBodyMapper.findBodyById(bodyId);
        ArticleBodyVo articleBodyVo = copy(articleBody);
        /**
         * 查询完文章后边应该新增阅读数
         * 查询完文章后本应该返回数据，这时进行了更新操作
         * 更新数据库会加写锁，阻塞其他操作性能降低，更新->增加次接口耗时
         * 如果更新出问题，不能影响查看文章操作
         * 因此
         * 可以把更新操作放入线程池中执行
         */
        threadService.updateArticleViewCount(articleMapper, article);
        return Result.success(articleBodyVo);
    }

    /**
     * 根据articleId查询
     *
     * @param articleIdList
     * @return
     */
    @Override
    public Result findArticleByIdList(List<Long> articleIdList) {
        //获取文章的avatar和title
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        List<Article> articleList = new ArrayList<>();
        for (Long articleId : articleIdList) {
            queryWrapper.eq(Article::getId, articleId);
            queryWrapper.select(Article::getId,Article::getAvatar,Article::getTitle);
            articleList.add(articleMapper.selectOne(queryWrapper));
        }
        return Result.success(copyList(articleList));
    }

    //copy 文章内容
    private ArticleBodyVo copy(ArticleBody articleBody) {
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        BeanUtils.copyProperties(articleBody, articleBodyVo);
        return articleBodyVo;
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    /**
     * 搜索建议
     *
     * @param kw
     * @return
     */
    @Override
    public Result findArticleByQkw(String kw) {
        System.out.println(kw);
        List<Article> findList = articleMapper.selectByKw(kw);
        System.out.println(findList);
        return Result.success(copyList(findList));
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
