package com.heart.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heart.domain.entity.Tag;
import com.heart.mapper.TagMapper;
import com.heart.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author Heart
 * @since 2023-07-16 16:36:59
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}

