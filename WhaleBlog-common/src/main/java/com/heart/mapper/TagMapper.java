package com.heart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heart.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author Heart
 * @since 2023-07-16 16:36:54
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}
