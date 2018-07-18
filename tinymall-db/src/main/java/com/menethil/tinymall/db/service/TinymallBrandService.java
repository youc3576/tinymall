package com.menethil.tinymall.db.service;

import com.github.pagehelper.PageHelper;
import com.menethil.tinymall.db.dao.TinymallBrandMapper;
import com.menethil.tinymall.db.domain.TinymallBrand;
import com.menethil.tinymall.db.domain.TinymallBrandExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TinymallBrandService {
    @Resource
    private TinymallBrandMapper brandMapper;

    public List<TinymallBrand> query(int offset, int limit) {
        TinymallBrandExample example = new TinymallBrandExample();
        example.or().andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return brandMapper.selectByExample(example);
    }

    public int queryTotalCount() {
        TinymallBrandExample example = new TinymallBrandExample();
        example.or().andDeletedEqualTo(false);
        return (int)brandMapper.countByExample(example);
    }

    public TinymallBrand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    public List<TinymallBrand> querySelective(String id, String name, Integer page, Integer size, String sort, String order) {
        TinymallBrandExample example = new TinymallBrandExample();
        TinymallBrandExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(id)){
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return brandMapper.selectByExample(example);
    }

    public int countSelective(String id, String name, Integer page, Integer size, String sort, String order) {
        TinymallBrandExample example = new TinymallBrandExample();
        TinymallBrandExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(id)){
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)brandMapper.countByExample(example);
    }

    public void updateById(TinymallBrand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    public void deleteById(Integer id) {
        brandMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(TinymallBrand brand) {
        brandMapper.insertSelective(brand);
    }

    public List<TinymallBrand> all() {
        return brandMapper.selectByExample(new TinymallBrandExample());
    }
}
