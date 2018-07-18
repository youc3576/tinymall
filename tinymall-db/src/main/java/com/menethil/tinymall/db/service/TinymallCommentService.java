package com.menethil.tinymall.db.service;

import com.github.pagehelper.PageHelper;
import com.menethil.tinymall.db.dao.TinymallCommentMapper;
import com.menethil.tinymall.db.domain.TinymallComment;
import com.menethil.tinymall.db.domain.TinymallCommentExample;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TinymallCommentService {
    @Resource
    private TinymallCommentMapper commentMapper;

    public List<TinymallComment> queryGoodsByGid(Integer id, int offset, int limit) {
        TinymallCommentExample example = new TinymallCommentExample();
        example.setOrderByClause(TinymallComment.Column.addTime.desc());
        example.or().andValueIdEqualTo(id).andTypeEqualTo((byte)0).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return commentMapper.selectByExample(example);
    }

    public int countGoodsByGid(Integer id, int offset, int limit) {
        TinymallCommentExample example = new TinymallCommentExample();
        example.or().andValueIdEqualTo(id).andTypeEqualTo((byte)0).andDeletedEqualTo(false);
        return (int)commentMapper.countByExample(example);
    }

    public List<TinymallComment> query(Byte type, Integer valueId, Integer showType, Integer offset, Integer limit) {
        TinymallCommentExample example = new TinymallCommentExample();
        example.setOrderByClause(TinymallComment.Column.addTime.desc());
        if(showType == 0) {
            example.or().andValueIdEqualTo(valueId).andTypeEqualTo(type).andDeletedEqualTo(false);
        }
        else if(showType == 1){
            example.or().andValueIdEqualTo(valueId).andTypeEqualTo(type).andHasPictureEqualTo(true).andDeletedEqualTo(false);
        }
        else{
            Assert.state(false, "showType不支持");
        }
        PageHelper.startPage(offset, limit);
        return commentMapper.selectByExample(example);
    }

    public int count(Byte type, Integer valueId, Integer showType, Integer offset, Integer size){
        TinymallCommentExample example = new TinymallCommentExample();
        if(showType == 0) {
            example.or().andValueIdEqualTo(valueId).andTypeEqualTo(type).andDeletedEqualTo(false);
        }
        else if(showType == 1){
            example.or().andValueIdEqualTo(valueId).andTypeEqualTo(type).andHasPictureEqualTo(true).andDeletedEqualTo(false);
        }
        else{
            Assert.state(false, "");
        }
        return (int)commentMapper.countByExample(example);
    }

    public Integer save(TinymallComment comment) {
        return commentMapper.insertSelective(comment);
    }


    public void update(TinymallComment comment) {
        commentMapper.updateByPrimaryKeySelective(comment);
    }


    public List<TinymallComment> querySelective(String userId, String valueId, Integer page, Integer size, String sort, String order) {
        TinymallCommentExample example = new TinymallCommentExample();
        example.setOrderByClause(TinymallComment.Column.addTime.desc());
        TinymallCommentExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(valueId)){
            criteria.andValueIdEqualTo(Integer.valueOf(valueId)).andTypeEqualTo((byte)0);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return commentMapper.selectByExample(example);
    }

    public int countSelective(String userId, String valueId, Integer page, Integer size, String sort, String order) {
        TinymallCommentExample example = new TinymallCommentExample();
        TinymallCommentExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(valueId)){
            criteria.andValueIdEqualTo(Integer.valueOf(valueId)).andTypeEqualTo((byte)0);
        }
        criteria.andDeletedEqualTo(false);

        return (int)commentMapper.countByExample(example);
    }

    public void updateById(TinymallComment comment) {
        commentMapper.updateByPrimaryKeySelective(comment);
    }

    public void deleteById(Integer id) {
        commentMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(TinymallComment comment) {
        commentMapper.insertSelective(comment);
    }

    public TinymallComment findById(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }
}
