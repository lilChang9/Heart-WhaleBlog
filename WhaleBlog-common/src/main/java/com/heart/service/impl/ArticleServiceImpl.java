package com.heart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heart.constants.article.ArticleConstants;
import com.heart.domain.ResponseResult;
import com.heart.domain.entity.Article;
import com.heart.domain.vo.HotArticleVo;
import com.heart.mapper.ArticleMapper;
import com.heart.service.ArticleService;
import com.heart.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public ResponseResult<List<HotArticleVo>> hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 文章必须是已发布状态
        queryWrapper.eq(Article::getStatus, ArticleConstants.ARTICAL_STATUS_PUBLISHED);
        // 根据浏览量降序排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 限制条数为5条
        queryWrapper.last(" LIMIT " + ArticleConstants.HOT_ARTICLE_NUM);
        List<Article> hotArticleList = this.list(queryWrapper);
        // 将文章集合进行Vo优化
        List<HotArticleVo> hotArticleVoList = BeanCopyUtils.copyBeanList(hotArticleList, HotArticleVo.class);
        return new ResponseResult<>(200, "成功获取5条热门文章信息", hotArticleVoList);
    }
}
