package com.heart.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页展示数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo<T> {

    private Long total;
    private List<T> rows;
}
