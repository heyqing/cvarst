package com.heyqing.cvarst_back.dao.pojo;

import lombok.Data;

/**
 * ClassName:Article
 * Package:com.heyqing.cvarst_back.dao.pojo
 * Description:
 *
 * @Date:2023/11/10
 * @Author:Heyqing
 */
@Data
public class Article {

    public static final int Article_TOP = 1;

    public static final int Article_Common = 0;

    private Long id;

    private String title;

//    private String summary;

    private Integer viewCounts;
    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *类别id
     */
    private Long categoryId;
    /**
     * 创建时间
     */
    private Long createDate;
    private String avatar;
}
