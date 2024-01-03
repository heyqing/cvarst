package com.heyqing.cvarst_back.controller.api;

import com.heyqing.cvarst_back.common.aop.LogAnnotation;
import com.heyqing.cvarst_back.service.ArticleService;
import com.heyqing.cvarst_back.service.PersonService;
import com.heyqing.cvarst_back.vo.Result;
import com.heyqing.cvarst_back.vo.params.PersonPageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName:PersonController
 * Package:com.heyqing.cvarst_back.controller.api
 * Description:
 *
 * @Date:2023/11/21
 * @Author:Heyqing
 */
@RestController
@RequestMapping("person")
public class PersonController {

    @Autowired
    private PersonService personService;
    @Autowired
    private ArticleService articleService;

    @LogAnnotation(module = "我的",operator = "我的喜欢")
    @PostMapping("like")
    public Result articleLike(@RequestBody PersonPageParam personPageParam) {
        return personService.findArticleLikeById(personPageParam);
    }

    @PostMapping("deleteLike")
    public Result deleteLikeByArticleId(@RequestBody Long articleId){
        return personService.deleteLikeByArticleId(articleId);
    }

    @PostMapping("deleteData")
    public Result deletePhotoByPhotoPath(@RequestBody String photoPath){
        return personService.deletePhotoByPhotoPath(photoPath);
    }

    @PostMapping("likebyid")
    public Result findArticleByIdList(@RequestBody List<Long> articleIdList){
        return  articleService.findArticleByIdList(articleIdList);
    }
    @PostMapping("allphoto")
    public Result findAllPhotoByToken(@RequestBody String token){
        return personService.findAllPhotoByToken(token);
    }
    @PostMapping("feedback")
    public Result feedback(@RequestBody String feedbackText){
        return personService.insertText(feedbackText);
    }
}
