package com.heart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heart.constants.link.LinkConstants;
import com.heart.domain.ResponseResult;
import com.heart.domain.entity.Link;
import com.heart.domain.vo.link.LinkVo;
import com.heart.mapper.LinkMapper;
import com.heart.service.LinkService;
import com.heart.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author Heart
 * @since 2023-06-20 10:41:56
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> linkWrapper = new LambdaQueryWrapper<>();
        // 只显示已经通过的友链
        linkWrapper.eq(Link::getStatus,LinkConstants.LINK_CHECK_PASSED);
        List<Link> linkList = list(linkWrapper);
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(linkList, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }
}

