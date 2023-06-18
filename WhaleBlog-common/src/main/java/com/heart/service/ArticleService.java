package com.heart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heart.domain.ResponseResult;
import com.heart.domain.entity.Article;
import com.heart.domain.vo.HotArticleVo;

import java.util.List;

public interface ArticleService extends IService<Article> {
    ResponseResult<List<HotArticleVo>> hotArticleList();
}
