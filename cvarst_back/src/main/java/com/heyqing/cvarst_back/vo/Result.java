package com.heyqing.cvarst_back.vo;

import com.heyqing.cvarst_back.dao.mapper.ArticleMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ClassName:Result
 * Package:com.heyqing.cvarst_back.vo
 * Description:
 *
 * @Date:2023/11/10
 * @Author:Heyqing
 */
@Data
@AllArgsConstructor
public class Result {

    private boolean success;

    private int code;

    private String msg;

    private Long  total;

    private Object data;

    public static  Result success(Object data){
        return new Result(true,200,"success",0l,data);
    }
    public static  Result success(Long total,Object data){
        return new Result(true,200,"success",total,data);
    }
    public static  Result fail(int code,String msg){
        return new Result(false,code,msg,null,null);
    }

}
