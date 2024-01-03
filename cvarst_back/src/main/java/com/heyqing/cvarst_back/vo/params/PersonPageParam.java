package com.heyqing.cvarst_back.vo.params;

import lombok.Data;

/**
 * ClassName:PersonPageParam
 * Package:com.heyqing.cvarst_back.vo.params
 * Description:
 *
 * @Date:2023/11/22
 * @Author:Heyqing
 */
@Data
public class PersonPageParam {
    
    private int page;

    private int pageSize;
    
    private String token;
    
}
