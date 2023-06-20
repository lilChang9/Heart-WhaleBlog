package com.heart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heart.domain.entity.Link;
import org.apache.ibatis.annotations.Mapper;


/**
 * 友链(Link)表数据库访问层
 *
 * @author Heart
 * @since 2023-06-20 10:41:56
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}
