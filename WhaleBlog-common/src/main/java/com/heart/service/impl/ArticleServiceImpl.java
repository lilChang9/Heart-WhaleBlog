package com.heart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heart.constants.article.ArticleConstants;
import com.heart.domain.ResponseResult;
import com.heart.domain.entity.Article;
import com.heart.domain.entity.Category;
import com.heart.domain.vo.ArticleVo;
import com.heart.domain.vo.HotArticleVo;
import com.heart.domain.vo.PageVo;
import com.heart.mapper.ArticleMapper;
import com.heart.service.ArticleService;
import com.heart.service.CategoryService;
import com.heart.utils.BeanCopyUtils;
import com.heart.utils.RedisCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private CategoryService categoryService;

    @Resource
    private RedisCache redisCache;

    @Override
    public ResponseResult<List<HotArticleVo>> hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 文章必须是已发布状态
        queryWrapper.eq(Article::getStatus, ArticleConstants.ARTICAL_STATUS_PUBLISHED);
        // 根据浏览量降序排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 限制条数为5条
        queryWrapper.last(" LIMIT " + ArticleConstants.HOT_ARTICLE_NUM);
        List<Article> hotArticleList = list(queryWrapper);
        // 将文章的浏览量替换成 Redis 中的数据
        hotArticleList.stream()
                .forEach(article -> {
                    Long viewCount = Long.valueOf(redisCache.getCacheObject(ArticleConstants.REDIS_VIEWCOUNT_PREFIX + article.getId()));
                    article.setViewCount(viewCount);
                });
        // 将文章集合进行Vo优化
        List<HotArticleVo> hotArticleVoList = BeanCopyUtils.copyBeanList(hotArticleList, HotArticleVo.class);
        return new ResponseResult<>(200, "成功获取5条热门文章信息", hotArticleVoList);
    }

    @Override
    public ResponseResult articleList(Long categoryId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        // 如果传入的categoryId为0,那么就将所有分类的文章进行分页展示
        // 如果不为0，就按 categoryId 进行分类展示
        articleWrapper.eq(categoryId > 0L, Article::getCategoryId, categoryId);
        // 查询的文章必须是已发布的状态
        articleWrapper.eq(Article::getStatus, ArticleConstants.ARTICAL_STATUS_PUBLISHED);
        // 按top字段进行排序，然后再按创建时间进行排序
        articleWrapper.orderByDesc(Article::getIsTop, Article::getCreateTime);
        Page<Article> page = new Page<>(pageNum, pageSize);
        Page<Article> pageRes = page(page, articleWrapper);
        List<ArticleVo> articleVos = BeanCopyUtils.copyBeanList(pageRes.getRecords(), ArticleVo.class);
        // 还要将每个文章对应分类 id 的名称查到,并封装到articleVo中
        articleVos.stream()
                .forEach(articleVo -> {
                    Category category = categoryService.getById(articleVo.getCategoryId());
                    articleVo.setCategoryName(category.getName());
                    Long viewCount = Long.valueOf(redisCache.getCacheObject(ArticleConstants.REDIS_VIEWCOUNT_PREFIX + articleVo.getId()));
                    articleVo.setViewCount(viewCount);
                });
        PageVo<ArticleVo> ArticleVoPageRes = new PageVo<>(pageRes.getTotal(), articleVos);
        return ResponseResult.okResult(ArticleVoPageRes);
    }

    @Override
    public ResponseResult articleDetail(Long id) {
        Article article = getById(id);
        ArticleVo articleVo = BeanCopyUtils.copyBean(article, ArticleVo.class);
        // 为当前文章Vo写入分类名
        Category category = categoryService.getById(article.getCategoryId());
        articleVo.setCategoryName(category.getName());
        Long viewCount = Long.valueOf(redisCache.getCacheObject(ArticleConstants.REDIS_VIEWCOUNT_PREFIX + id));
        articleVo.setViewCount(viewCount);
        return ResponseResult.okResult(articleVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        // 更新文章浏览量
        Long viewCount = Long.valueOf(redisCache.getCacheObject(ArticleConstants.REDIS_VIEWCOUNT_PREFIX + id));
        redisCache.setCacheObject(ArticleConstants.REDIS_VIEWCOUNT_PREFIX + id, viewCount + 1);
        return ResponseResult.okResult();
    }
}
