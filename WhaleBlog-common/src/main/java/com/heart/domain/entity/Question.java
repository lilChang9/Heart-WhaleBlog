package com.heart.domain.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Question)表实体类
 *
 * @author Heart
 * @since 2023-06-18 21:36:53
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("question")
public class Question  {
    //问题id
    @TableId
    private Long id;
    //问题
    private String question;
    //问题详细补充
    private String detail;
    //问题标签
    private String tag;
    //分类id
    private String categoryId;
    //浏览量
    private Integer viewCount;
    //关注此问题人数
    private Integer followerCount;
    //评论数
    private Integer commentCount;
    //点赞数
    private Integer thumbCount;
    
    private Long createBy;
    
    private Date createTime;
    
    private Long updateBy;
    
    private Date updateTime;
    //删除标志(0代表未删除，1代表已删除)
    private Integer delFlag;



}
