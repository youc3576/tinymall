package com.menethil.tinymall.db.service;

import com.github.pagehelper.PageHelper;
import com.menethil.tinymall.db.dao.TinymallFootprintMapper;
import com.menethil.tinymall.db.domain.TinymallFootprint;
import com.menethil.tinymall.db.domain.TinymallFootprintExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TinymallFootprintService {
    @Resource
    private TinymallFootprintMapper footprintMapper;

    public List<TinymallFootprint> queryByAddTime(Integer userId, Integer page, Integer size) {
        TinymallFootprintExample example = new TinymallFootprintExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        example.setOrderByClause(TinymallFootprint.Column.addTime.desc());
        PageHelper.startPage(page, size);
        return footprintMapper.selectByExample(example);
    }

    public int countByAddTime(Integer userId,Integer page, Integer size) {
        TinymallFootprintExample example = new TinymallFootprintExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return (int)footprintMapper.countByExample(example);
    }

    public TinymallFootprint findById(Integer id) {
        return footprintMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id){
        footprintMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(TinymallFootprint footprint) {
        footprintMapper.insertSelective(footprint);
    }

    public List<TinymallFootprint> querySelective(String userId, String goodsId, Integer page, Integer size, String sort, String order) {
        TinymallFootprintExample example = new TinymallFootprintExample();
        TinymallFootprintExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(goodsId)){
            criteria.andGoodsIdEqualTo(Integer.valueOf(goodsId));
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return footprintMapper.selectByExample(example);
    }

    public int countSelective(String userId, String goodsId, Integer page, Integer size, String sort, String order) {
        TinymallFootprintExample example = new TinymallFootprintExample();
        TinymallFootprintExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(goodsId)){
            criteria.andGoodsIdEqualTo(Integer.valueOf(goodsId));
        }
        criteria.andDeletedEqualTo(false);

        return (int)footprintMapper.countByExample(example);
    }

    public void updateById(TinymallFootprint collect) {
        footprintMapper.updateByPrimaryKeySelective(collect);
    }

}
