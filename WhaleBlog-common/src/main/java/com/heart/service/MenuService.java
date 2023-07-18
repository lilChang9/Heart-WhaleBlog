package com.heart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heart.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author Heart
 * @since 2023-07-16 19:15:58
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long userId);
}
