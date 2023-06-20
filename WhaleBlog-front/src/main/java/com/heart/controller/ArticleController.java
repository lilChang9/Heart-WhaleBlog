package com.heart.controller;

import com.heart.domain.ResponseResult;
import com.heart.domain.vo.HotArticleVo;
import com.heart.service.ArticleService;
import org.springframework.web.bind.annotation.*;

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


    /**
     * 分页查询文章
     * @param categoryId 文章分类id
     * @param pageNum 页码
     * @param pageSize 每页展示条数
     * @return
     */
    @GetMapping("/articleList")
    public ResponseResult getArticleList(@RequestParam("categoryId") Long categoryId,
                                         @RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize){
        return articleService.articleList(categoryId,pageNum,pageSize);
    }

    /**
     * 根据文章id查看文章详情
     * @param id 文章id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.articleDetail(id);
    }

}
