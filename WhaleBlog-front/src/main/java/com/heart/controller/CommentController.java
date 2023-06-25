package com.heart.controller;

import com.heart.domain.ResponseResult;
import com.heart.domain.entity.Comment;
import com.heart.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,
                                      @RequestParam("articleId") Long articleId){
        return commentService.commentList(pageNum,pageSize,articleId);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult getLinkCommentList(@RequestParam("pageNum") Integer pageNum,
                                             @RequestParam("pageSize") Integer pageSize){
        return commentService.linkCommentList(pageNum,pageSize);
    }


    @PostMapping()
    public ResponseResult comment(@RequestBody Comment comment){
        return commentService.comment(comment);
    }


}
