package com.heart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heart.domain.entity.Comment;
import org.apache.ibatis.annotations.Mapper;


/**
 * 评论表(Comment)表数据库访问层
 *
 * @author Heart
 * @since 2023-06-21 10:05:06
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
