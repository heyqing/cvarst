package com.heyqing.cvarst_back.vo.params;

import com.heyqing.cvarst_back.vo.CategoryVo;
import lombok.Data;

import java.util.List;

/**
 * ClassName:ArticleParam
 * Package:com.heyqing.cvarst_back.vo.params
 * Description:
 *
 * @Date:2023/11/16
 * @Author:Heyqing
 */
@Data
public class ArticleParam {
    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String title;

    private String avatar;
}
