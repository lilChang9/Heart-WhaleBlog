package com.heart.controller;

import com.heart.domain.ResponseResult;
import com.heart.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 获取所有的分类
     * @return 分类列表(id,name)
     */
    @GetMapping("/categoryList")
    public ResponseResult categoryList(){
        return categoryService.categoryList();
    }

}
