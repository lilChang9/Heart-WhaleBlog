package com.heart.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVo {

    private Long id;

    //标题
    private String title;

    //文章摘要
    private String summary;

    //所属分类id
    private Long categoryId;

    //所属分类id
    private String categoryName;

    //缩略图
    private String thumbnail;

    //文章详情
    private String content;

    //是否置顶（0否，1是）
    private String isTop;

    //访问量
    private Long viewCount;

    //是否允许评论 1是，0否
    private String isComment;

    private Date createTime;
}
