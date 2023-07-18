package com.heart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heart.constants.menu.MenuConstants;
import com.heart.domain.entity.Menu;
import com.heart.mapper.MenuMapper;
import com.heart.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author Heart
 * @since 2023-07-16 19:15:58
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<String> selectPermsByUserId(Long userId) {
        // 如果用户id为 1,则其为用户管理员，返回所有menuType为C或F的
        // visible为0的权限信息
        if(userId == 1L){
            LambdaQueryWrapper<Menu> menuWrapper = new LambdaQueryWrapper<>();
            menuWrapper.eq(Menu::getMenuType, MenuConstants.MENU_TYPE).or().eq(Menu::getMenuType,MenuConstants.BUTTON_TYPE);
            menuWrapper.eq(Menu::getVisible,MenuConstants.VISIBLE);
            menuWrapper.eq(Menu::getStatus,MenuConstants.STATUS_NORMAL);
            List<Menu> menuList = list(menuWrapper);
            List<String> perms = menuList.stream()
                    .map(menu -> menu.getPerms()).collect(Collectors.toList());
            return perms;
        }
        return menuMapper.selectPermsByUserId(userId);
    }
}

