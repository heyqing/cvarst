package com.heyqing.cvarst_back.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heyqing.cvarst_back.dao.pojo.ArticleBody;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName:ArticleBodyMapper
 * Package:com.heyqing.cvarst_back.dao.mapper
 * Description:
 *
 * @Date:2023/11/19
 * @Author:Heyqing
 */
@Mapper
public interface ArticleBodyMapper extends BaseMapper<ArticleBody> {
    ArticleBody findBodyById(Long bodyId);
}
