package com.heyqing.cvarst_back.vo.params;

import lombok.Data;

/**
 * ClassName:PageParams
 * Package:com.heyqing.cvarst_back.vo.params
 * Description:
 *
 * @Date:2023/11/10
 * @Author:Heyqing
 */
@Data
public class PageParams {
    private int page = 1;

    private int pageSize = 10;

    private Long categoryId;

    private Long tagId;

    private String year;

    private String month;

    public String getMonth(){
        if(this.month != null && this.month.length() == 1){
            return "0"+this.month;
        }
        return this.month;
    }
}
