package com.heyqing.cvarst_back.dao.pojo;

import lombok.Data;

/**
 * ClassName:ArticleBody
 * Package:com.heyqing.cvarst_back.dao.pojo
 * Description:
 *
 * @Date:2023/11/12
 * @Author:Heyqing
 */
@Data
public class ArticleBody {
    private Long id;
    private String style;
    private String content;
    private String contentHTML;
    private Long articleId;
}
