package com.heyqing.cvarst_back.controller.api;

import com.heyqing.cvarst_back.service.CategoryService;
import com.heyqing.cvarst_back.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:CategoryController
 * Package:com.heyqing.cvarst_back.controller.api
 * Description:
 *
 * @Date:2023/11/16
 * @Author:Heyqing
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public Result findAll(){
        return categoryService.findAll();
    }
}
