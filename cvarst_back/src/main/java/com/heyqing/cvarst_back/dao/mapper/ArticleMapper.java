package com.heyqing.cvarst_back.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heyqing.cvarst_back.dao.dos.Archives;
import com.heyqing.cvarst_back.dao.pojo.Article;
import com.heyqing.cvarst_back.dao.pojo.ArticleBody;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * ClassName:ArticleMapper
 * Package:com.heyqing.cvarst_back.dao.pojo.mapper
 * Description:
 *
 * @Date:2023/11/10
 * @Author:Heyqing
 */
@Mapper
public interface ArticleMapper  extends BaseMapper<Article> {

    //获取文章总数
    Long allArticle();
    List<Archives> listArchives();
    //通过XML文件查询
    IPage<Article> listArticle(Page<Article> page, Long categoryId, Long tagId, String year, String month);

    List<Article> selectByKw(String kw);
}
