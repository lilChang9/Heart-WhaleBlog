package com.heart.domain.vo.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {

    private Long id;
    //文章id
    private Long articleId;
    // 子评论
    private List<CommentVo> children;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    // 所回复的目标评论的 username
    private String toCommentUserName;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //回复目标评论id
    private Long toCommentId;

    private String username;

    private Long createBy;

    private Date createTime;
}
