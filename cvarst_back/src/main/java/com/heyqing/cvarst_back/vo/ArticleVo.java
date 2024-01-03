package com.heyqing.cvarst_back.vo;

import lombok.Data;

import java.util.List;

/**
 * ClassName:ArticleVo
 * Package:com.heyqing.cvarst_back.vo
 * Description:
 *
 * @Date:2023/11/10
 * @Author:Heyqing
 */
@Data
public class ArticleVo {
    private String  id;

    private String title;

//    private String summary;

    private Integer viewCounts;
    /**
     * 创建时间
     */
    private String createDate;

    private String author = "何以晴";

    private ArticleBodyVo body;

    private CategoryVo category;

    private String avatar;
}
