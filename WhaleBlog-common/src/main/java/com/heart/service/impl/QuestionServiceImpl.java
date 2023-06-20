package com.heart.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heart.domain.ResponseResult;
import com.heart.domain.entity.Question;
import com.heart.enums.AppHttpCodeEnum;
import com.heart.mapper.QuestionMapper;
import com.heart.service.QuestionService;
import org.springframework.stereotype.Service;

/**
 * (Question)表服务实现类
 *
 * @author Heart
 * @since 2023-06-18 21:14:07
 */
@Service("questionService")
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Override
    public ResponseResult publishQuestion(Question question) {
        save(question);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS.getCode(),"保存成功");
    }
}

