package com.heyqing.cvarst_back.controller.api;

import com.heyqing.cvarst_back.common.aop.LogAnnotation;
import com.heyqing.cvarst_back.dao.pojo.SysUser;
import com.heyqing.cvarst_back.service.LoginService;
import com.heyqing.cvarst_back.vo.Result;
import com.heyqing.cvarst_back.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:LoginController
 * Package:com.heyqing.cvarst_back.controller.api
 * Description:
 *
 * @Date:2023/11/11
 * @Author:Heyqing
 */
@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 个人主页     登录
     * @param loginParam
     * @return
     */
    @LogAnnotation(module = "登录",operator = "注册并登录")
    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        return loginService.login(loginParam);
    }

    /**
     * 检查token合法性
     * @param token
     * @return
     */
    @LogAnnotation(module = "登录",operator = "检查token")
    @PostMapping("checkToken")
    public Result checkToken(@RequestBody String token){
        return loginService.checkToken(token);
    }
}
