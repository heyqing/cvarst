package com.heyqing.cvarst_back.vo.params;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * ClassName:logoutParam
 * Package:com.heyqing.cvarst_back.vo.params
 * Description:
 *
 * @Date:2023/11/29
 * @Author:Heyqing
 */
@Data
public class LogoutParam {

    private List<Long> articleList;

    private List<String> photoList;

    private String token;

}
