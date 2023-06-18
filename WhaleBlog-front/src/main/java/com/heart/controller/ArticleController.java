package com.heart.controller;

import com.heart.domain.ResponseResult;
import com.heart.domain.entity.Article;
import com.heart.domain.vo.HotArticleVo;
import com.heart.service.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 获取5条热门文章列表
     * @return 热门文章列表集合
     */
    @GetMapping("/hotArticleList")
    public ResponseResult<List<HotArticleVo>> getHotArticle(){
        return articleService.hotArticleList();
    }


}
