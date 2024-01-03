package com.heyqing.cvarst_back.controller.api;

import com.heyqing.cvarst_back.common.aop.LogAnnotation;
import com.heyqing.cvarst_back.service.LogoutService;
import com.heyqing.cvarst_back.vo.Result;
import com.heyqing.cvarst_back.vo.params.LogoutParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName:LogoutController
 * Package:com.heyqing.cvarst_back.controller.api
 * Description:
 *
 * @Date:2023/11/11
 * @Author:Heyqing
 */
@RestController
@RequestMapping("logout")
public class LogoutController {
    @Autowired
    private LogoutService logoutService;
    @LogAnnotation(module = "登录",operator = "退出登录")
    @PostMapping
    public Result logout(@RequestBody LogoutParam logoutParam){
        return logoutService.logout(logoutParam);
    }
}
