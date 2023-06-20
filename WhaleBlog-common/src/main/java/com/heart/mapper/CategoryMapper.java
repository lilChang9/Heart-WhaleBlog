package com.heart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heart.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author Heart
 * @since 2023-06-18 16:07:45
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
