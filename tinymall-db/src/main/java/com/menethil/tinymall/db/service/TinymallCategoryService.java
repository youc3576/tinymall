package com.menethil.tinymall.db.service;

import com.github.pagehelper.PageHelper;
import com.menethil.tinymall.db.dao.TinymallCategoryMapper;
import com.menethil.tinymall.db.domain.TinymallCategory;
import com.menethil.tinymall.db.domain.TinymallCategoryExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TinymallCategoryService {
    @Resource
    private TinymallCategoryMapper categoryMapper;

    public List<TinymallCategory> queryL1WithoutRecommend(int offset, int limit) {
        TinymallCategoryExample example = new TinymallCategoryExample();
        example.or().andLevelEqualTo("L1").andNameNotEqualTo("推荐").andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return categoryMapper.selectByExample(example);
    }

    public List<TinymallCategory> queryL1(int offset, int limit) {
        TinymallCategoryExample example = new TinymallCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return categoryMapper.selectByExample(example);
    }

    public List<TinymallCategory> queryL1() {
        TinymallCategoryExample example = new TinymallCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    public List<TinymallCategory> queryByPid(Integer pid) {
        TinymallCategoryExample example = new TinymallCategoryExample();
        example.or().andPidEqualTo(pid).andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    public List<TinymallCategory> queryL2ByIds(List<Integer> ids) {
        TinymallCategoryExample example = new TinymallCategoryExample();
        example.or().andIdIn(ids).andLevelEqualTo("L2").andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    public TinymallCategory findById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    public List<TinymallCategory> querySelective(String id, String name, Integer page, Integer size, String sort, String order) {
        TinymallCategoryExample example = new TinymallCategoryExample();
        TinymallCategoryExample.Criteria criteria = example.createCriteria();

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
        return categoryMapper.selectByExample(example);
    }

    public int countSelective(String id, String name, Integer page, Integer size, String sort, String order) {
        TinymallCategoryExample example = new TinymallCategoryExample();
        TinymallCategoryExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(id)){
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)categoryMapper.countByExample(example);
    }

    public void updateById(TinymallCategory category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    public void deleteById(Integer id) {
        categoryMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(TinymallCategory category) {
        categoryMapper.insertSelective(category);
    }

    private TinymallCategory.Column[] CHANNEL = {TinymallCategory.Column.id, TinymallCategory.Column.name, TinymallCategory.Column.iconUrl};
    public List<TinymallCategory> queryChannel() {
        TinymallCategoryExample example = new TinymallCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        return categoryMapper.selectByExampleSelective(example, CHANNEL);
    }
}
