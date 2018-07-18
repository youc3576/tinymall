package com.menethil.tinymall.db.service;

import com.github.pagehelper.PageHelper;
import com.menethil.tinymall.db.dao.*;
import com.menethil.tinymall.db.domain.TinymallRegion;
import com.menethil.tinymall.db.domain.TinymallRegionExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TinymallRegionService {
    @Resource
    private TinymallRegionMapper regionMapper;

    public List<TinymallRegion> queryByPid(Integer parentId) {
        TinymallRegionExample example = new TinymallRegionExample();
        example.or().andPidEqualTo(parentId);
        return regionMapper.selectByExample(example);
    }

    public TinymallRegion findById(Integer id) {
        return regionMapper.selectByPrimaryKey(id);
    }

    public List<TinymallRegion> querySelective(String name, Integer code, Integer page, Integer size, String sort, String order) {
        TinymallRegionExample example = new TinymallRegionExample();
        TinymallRegionExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isEmpty(code)){
            criteria.andCodeEqualTo(code);
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return regionMapper.selectByExample(example);
    }

    public int countSelective(String name, Integer code, Integer page, Integer size, String sort, String order) {
        TinymallRegionExample example = new TinymallRegionExample();
        TinymallRegionExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(code != null){
            criteria.andCodeEqualTo(code);
        }
        return (int)regionMapper.countByExample(example);
    }
}
