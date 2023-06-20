package com.heart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heart.domain.ResponseResult;
import com.heart.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author Heart
 * @since 2023-06-20 10:41:56
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

}
