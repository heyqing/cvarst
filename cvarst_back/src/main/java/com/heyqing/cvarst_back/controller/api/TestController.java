package com.heyqing.cvarst_back.controller.api;

import com.heyqing.cvarst_back.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:TestController
 * Package:com.heyqing.cvarst_back.controller.api
 * Description:
 *
 * @Date:2023/11/11
 * @Author:Heyqing
 */
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping
    public Result test(){
        return Result.success(null);
    }
}
