package com.heart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heart.domain.ResponseResult;
import com.heart.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author Heart
 * @since 2023-06-18 16:07:47
 */
public interface CategoryService extends IService<Category> {

    ResponseResult categoryList();

}
