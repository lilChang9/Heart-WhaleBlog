package com.heart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heart.domain.ResponseResult;
import com.heart.domain.entity.Question;


/**
 * (Question)表服务接口
 *
 * @author Heart
 * @since 2023-06-18 21:14:06
 */
public interface QuestionService extends IService<Question> {

    ResponseResult publishQuestion(Question question);
}
