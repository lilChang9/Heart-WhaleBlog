package com.heart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heart.constants.comment.CommentConstants;
import com.heart.domain.ResponseResult;
import com.heart.domain.entity.Comment;
import com.heart.domain.entity.User;
import com.heart.domain.vo.PageVo;
import com.heart.domain.vo.comment.CommentVo;
import com.heart.mapper.CommentMapper;
import com.heart.mapper.UserMapper;
import com.heart.service.CommentService;
import com.heart.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Consumer;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author Heart
 * @since 2023-06-21 10:05:07
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private UserMapper userMapper;

    @Override
    public ResponseResult commentList(Integer pageNum, Integer pageSize, Long articleId) {
        // 分页查询当前文章评论
        LambdaQueryWrapper<Comment> commentWrapper = new LambdaQueryWrapper<>();
        commentWrapper.eq(Comment::getType,CommentConstants.ARTICLE_COMMENT);
        commentWrapper.eq(Comment::getArticleId,articleId);
        // 先查询当前文章下的所有根评论
        commentWrapper.eq(Comment::getRootId, CommentConstants.COMMENT_ROOT);
        Page<Comment> page = new Page<>(pageNum,pageSize);
        page(page,commentWrapper);
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(page.getRecords(), CommentVo.class);
        // 将根评论的用户名以及子评论进行封装
        commentVos.stream().forEach(commentVo -> {
            User user = userMapper.selectById(commentVo.getCreateBy());
            commentVo.setUsername(user.getNickName());
            LambdaQueryWrapper<Comment> childCommentWrapper = new LambdaQueryWrapper<>();
            childCommentWrapper.eq(Comment::getType,CommentConstants.ARTICLE_COMMENT);
            childCommentWrapper.eq(Comment::getRootId,commentVo.getId());
            List<Comment> child = list(childCommentWrapper);
            List<CommentVo> childCommentVo = BeanCopyUtils.copyBeanList(child, CommentVo.class);
            // 为子评论封装 username 和 toCommentUserName
            childCommentVo.stream().forEach(childVo -> {
                User childUser = userMapper.selectById(childVo.getCreateBy());
                childVo.setUsername(childUser.getUserName());
                childVo.setToCommentUserName(commentVo.getUsername());
            });
            // 将子评论封装到根评论中
            commentVo.setChildren(childCommentVo);
        });

        PageVo<CommentVo> pageVo = new PageVo<>(page.getTotal(), commentVos);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult comment(Comment comment) {
        save(comment);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        // 首先将类型为友链评论的根评论查出来
        LambdaQueryWrapper<Comment> commentWrapper = new LambdaQueryWrapper<>();
        commentWrapper.eq(Comment::getType,CommentConstants.LINK_COMMENT);
        commentWrapper.eq(Comment::getRootId,CommentConstants.COMMENT_ROOT);
        Page<Comment> page = new Page<>(pageNum,pageSize);
        page(page,commentWrapper);
        // 所有的根评论
        List<Comment> commentList = page.getRecords();
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(commentList, CommentVo.class);
        // 封装 username
        commentVos.stream()
                .forEach(commentVo -> {
                    User user = userMapper.selectById(commentVo.getCreateBy());
                    commentVo.setUsername(user.getNickName());
                });
        // 封装 children
        commentVos.stream()
                .forEach(commentVo -> {
                    LambdaQueryWrapper<Comment> childWrapper = new LambdaQueryWrapper<>();
                    childWrapper.eq(Comment::getType,CommentConstants.LINK_COMMENT);
                    childWrapper.eq(Comment::getRootId,commentVo.getId());
                    List<Comment> childCommentList = list(childWrapper);
                    List<CommentVo> childCommentVos = BeanCopyUtils.copyBeanList(childCommentList, CommentVo.class);
                    commentVo.setChildren(childCommentVos);
                    childCommentVos.stream().forEach(childCommentVo -> {
                        // 为子评论封装 username 和 toCommentUserName
                        User user = userMapper.selectById(childCommentVo.getCreateBy());
                        childCommentVo.setUsername(user.getNickName());
                        childCommentVo.setToCommentUserName(commentVo.getUsername());
                    });
                });

        return ResponseResult.okResult(new PageVo<>(page.getTotal(),commentVos));
    }
}

