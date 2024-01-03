package com.heyqing.cvarst_back.dao.pojo;

import lombok.Data;

/**
 * ClassName:Category
 * Package:com.heyqing.cvarst_back.dao.pojo
 * Description:
 *
 * @Date:2023/11/16
 * @Author:Heyqing
 */
@Data
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
