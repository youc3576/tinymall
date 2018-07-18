package com.menethil.tinymall.db.service;

import com.github.pagehelper.PageHelper;
import com.menethil.tinymall.db.dao.TinymallStorageMapper;
import com.menethil.tinymall.db.domain.TinymallStorage;
import com.menethil.tinymall.db.domain.TinymallStorageExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TinymallStorageService {
    @Autowired
    private TinymallStorageMapper storageMapper;

    public void deleteByKey(String key) {
        TinymallStorageExample example = new TinymallStorageExample();
        example.or().andKeyEqualTo(key);
        storageMapper.logicalDeleteByExample(example);
    }

    public void add(TinymallStorage storageInfo) {
        storageMapper.insertSelective(storageInfo);
    }

    public TinymallStorage findByName(String filename) {
        TinymallStorageExample example = new TinymallStorageExample();
        example.or().andNameEqualTo(filename).andDeletedEqualTo(false);
        return storageMapper.selectOneByExample(example);
    }

    public TinymallStorage findByKey(String key) {
        TinymallStorageExample example = new TinymallStorageExample();
        example.or().andKeyEqualTo(key).andDeletedEqualTo(false);
        return storageMapper.selectOneByExample(example);
    }

    public void update(TinymallStorage storageInfo) {
        storageMapper.updateByPrimaryKeySelective(storageInfo);
    }


    public TinymallStorage findById(Integer id) {
        return storageMapper.selectByPrimaryKey(id);
    }

    public List<TinymallStorage> querySelective(String key, String name, Integer page, Integer limit, String sort, String order) {
        TinymallStorageExample example = new TinymallStorageExample();
        TinymallStorageExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(key)){
            criteria.andKeyEqualTo(key);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return storageMapper.selectByExample(example);
    }

    public int countSelective(String key, String name, Integer page, Integer size, String sort, String order) {
        TinymallStorageExample example = new TinymallStorageExample();
        TinymallStorageExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(key)){
            criteria.andKeyEqualTo(key);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)storageMapper.countByExample(example);
    }
}
