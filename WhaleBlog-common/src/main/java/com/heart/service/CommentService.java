package com.heart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heart.domain.ResponseResult;
import com.heart.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author Heart
 * @since 2023-06-21 10:05:07
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(Integer pageNum, Integer pageSize, Long articleId);


    ResponseResult comment(Comment comment);

    ResponseResult linkCommentList(Integer pageNum, Integer pageSize);
}
