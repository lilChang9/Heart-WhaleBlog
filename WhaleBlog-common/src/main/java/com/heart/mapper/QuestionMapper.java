package com.heart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heart.domain.entity.Question;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Question)表数据库访问层
 *
 * @author Heart
 * @since 2023-06-18 21:14:02
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

}
