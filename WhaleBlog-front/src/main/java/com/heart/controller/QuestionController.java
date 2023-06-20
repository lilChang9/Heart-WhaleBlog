package com.heart.controller;

import com.heart.domain.ResponseResult;
import com.heart.domain.entity.Question;
import com.heart.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @PostMapping("/publish")
    public ResponseResult publishQuestion(Question question){
        return questionService.publishQuestion(question);
    }

    @GetMapping("/publish")
    public String question(){
        return "question";
    }
}
