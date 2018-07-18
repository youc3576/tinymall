package com.menethil.tinymall.db.service;

import com.github.pagehelper.PageHelper;
import com.menethil.tinymall.db.dao.TinymallCollectMapper;
import com.menethil.tinymall.db.domain.TinymallCollect;
import com.menethil.tinymall.db.domain.TinymallCollectExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TinymallCollectService {
    @Resource
    private TinymallCollectMapper collectMapper;

    public int count(int uid, Integer gid) {
        TinymallCollectExample example = new TinymallCollectExample();
        example.or().andUserIdEqualTo(uid).andValueIdEqualTo(gid).andDeletedEqualTo(false);
        return (int)collectMapper.countByExample(example);
    }

    public List<TinymallCollect> queryByType(Integer userId, Byte type, Integer page, Integer size) {
        TinymallCollectExample example = new TinymallCollectExample();
        example.or().andUserIdEqualTo(userId).andTypeEqualTo(type).andDeletedEqualTo(false);
        example.setOrderByClause(TinymallCollect.Column.addTime.desc());
        PageHelper.startPage(page, size);
        return collectMapper.selectByExample(example);
    }

    public int countByType(Integer userId, Byte type) {
        TinymallCollectExample example = new TinymallCollectExample();
        example.or().andUserIdEqualTo(userId).andTypeEqualTo(type).andDeletedEqualTo(false);
        return (int)collectMapper.countByExample(example);
    }

    public TinymallCollect queryByTypeAndValue(Integer userId, Byte type, Integer valueId) {
        TinymallCollectExample example = new TinymallCollectExample();
        example.or().andUserIdEqualTo(userId).andValueIdEqualTo(valueId).andTypeEqualTo(type).andDeletedEqualTo(false);
        return collectMapper.selectOneByExample(example);
    }

    public void deleteById(Integer id) {
        collectMapper.logicalDeleteByPrimaryKey(id);
    }

    public int add(TinymallCollect collect) {
        return collectMapper.insertSelective(collect);
    }

    public List<TinymallCollect> querySelective(String userId, String valueId, Integer page, Integer size, String sort, String order) {
        TinymallCollectExample example = new TinymallCollectExample();
        TinymallCollectExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(valueId)){
            criteria.andValueIdEqualTo(Integer.valueOf(valueId));
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return collectMapper.selectByExample(example);
    }

    public int countSelective(String userId, String valueId, Integer page, Integer size, String sort, String order) {
        TinymallCollectExample example = new TinymallCollectExample();
        TinymallCollectExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(valueId)){
            criteria.andValueIdEqualTo(Integer.valueOf(valueId));
        }
        criteria.andDeletedEqualTo(false);

        return (int)collectMapper.countByExample(example);
    }

    public void updateById(TinymallCollect collect) {
        collectMapper.updateByPrimaryKeySelective(collect);
    }

    public TinymallCollect findById(Integer id) {
        return collectMapper.selectByPrimaryKey(id);
    }
}
