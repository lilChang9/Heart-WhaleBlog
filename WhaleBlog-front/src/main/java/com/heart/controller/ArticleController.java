package com.heart.controller;

import com.heart.domain.entity.Article;
import com.heart.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @GetMapping("/")
    public String test(Model model){
        List<Article> list = articleService.list();
        model.addAttribute("articles",list);
        return "index";
    }
}
