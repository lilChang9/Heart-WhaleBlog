package com.heart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heart.constants.article.ArticleConstants;
import com.heart.constants.category.CategoryConstants;
import com.heart.domain.ResponseResult;
import com.heart.domain.entity.Article;
import com.heart.domain.vo.CategoryVo;
import com.heart.domain.entity.Category;
import com.heart.mapper.CategoryMapper;
import com.heart.service.ArticleService;
import com.heart.service.CategoryService;
import com.heart.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author Heart
 * @since 2023-06-18 16:07:47
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private ArticleService articleService;

    @Override
    public ResponseResult categoryList() {
        // 当前分类下必须存在正式发布的文章才能被查到
        // (1) 从文章表中查询所有已经正式发布文章的分类id
        QueryWrapper<Article> articleWrapper = new QueryWrapper<>();
        articleWrapper.select(" DISTINCT category_id ")
                .lambda()
                .eq(Article::getStatus,ArticleConstants.ARTICAL_STATUS_PUBLISHED);
        List<Article> articleList = articleService.list(articleWrapper);
        // (2) 将查询出来文章对象映射成一个 categoryIdList
        List<Long> categoryIdList = articleList
                .stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toList());

        LambdaQueryWrapper<Category> categoryWrapper = new LambdaQueryWrapper<>();
        // 查询到的分类必须是正常状态的
        categoryWrapper.eq(Category::getStatus, CategoryConstants.CATEGORY_STATUS_NORMAL);
        // 分类下必须有正式文章发布的
        categoryWrapper.in(Category::getId,categoryIdList);
        List<Category> categoryList = list(categoryWrapper);
        // 将categoryList 拷贝成只有 id,name 属性的 categoryVoList
        List<CategoryVo> categoryVoList = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        return ResponseResult.okResult(categoryVoList);
    }
}

