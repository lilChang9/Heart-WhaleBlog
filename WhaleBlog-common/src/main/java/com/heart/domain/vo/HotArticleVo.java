package com.heart.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 热门文章Vo(侧边栏展示)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArticleVo {

    private Long id;

    //标题
    private String title;

    //访问量
    private Long viewCount;

    private Date createTime;
}
