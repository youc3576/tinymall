package com.menethil.tinymall.db.service;

import com.github.pagehelper.PageHelper;
import com.menethil.tinymall.db.dao.TinymallKeywordMapper;
import com.menethil.tinymall.db.domain.TinymallKeyword;
import com.menethil.tinymall.db.domain.TinymallKeywordExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TinymallKeywordService {
    @Resource
    private TinymallKeywordMapper keywordsMapper;

    public List<TinymallKeyword> queryDefaults() {
        TinymallKeywordExample example = new TinymallKeywordExample();
        example.or().andIsDefaultEqualTo(true).andDeletedEqualTo(false);
        return keywordsMapper.selectByExample(example);
    }

    public TinymallKeyword queryDefault() {
        TinymallKeywordExample example = new TinymallKeywordExample();
        example.or().andIsDefaultEqualTo(true).andDeletedEqualTo(false);
        return keywordsMapper.selectOneByExample(example);
    }

    public List<TinymallKeyword> queryHots() {
        TinymallKeywordExample example = new TinymallKeywordExample();
        example.or().andIsHotEqualTo(true).andDeletedEqualTo(false);
        return keywordsMapper.selectByExample(example);
    }

    public List<TinymallKeyword> queryByKeyword(String keyword, Integer page, Integer size) {
        TinymallKeywordExample example = new TinymallKeywordExample();
        example.setDistinct(true);
        example.or().andKeywordLike("%" + keyword + "%").andDeletedEqualTo(false);
        PageHelper.startPage(page, size);
        return keywordsMapper.selectByExampleSelective(example, TinymallKeyword.Column.keyword);
    }

    public List<TinymallKeyword> querySelective(String keyword, String url, Integer page, Integer limit, String sort, String order) {
        TinymallKeywordExample example = new TinymallKeywordExample();
        TinymallKeywordExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(keyword)) {
            criteria.andKeywordLike("%" + keyword + "%");
        }
        if (!StringUtils.isEmpty(url)) {
            criteria.andUrlLike("%" + url + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return keywordsMapper.selectByExample(example);
    }

    public int countSelective(String keyword, String url, Integer page, Integer limit, String sort, String order) {
        TinymallKeywordExample example = new TinymallKeywordExample();
        TinymallKeywordExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(keyword)) {
            criteria.andKeywordLike("%" + keyword + "%");
        }
        if (!StringUtils.isEmpty(url)) {
            criteria.andUrlLike("%" + url + "%");
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, limit);
        return (int)keywordsMapper.countByExample(example);
    }

    public void add(TinymallKeyword keywords) {
        keywordsMapper.insertSelective(keywords);
    }

    public TinymallKeyword findById(Integer id) {
        return keywordsMapper.selectByPrimaryKey(id);
    }

    public void updateById(TinymallKeyword keywords) {
        keywordsMapper.updateByPrimaryKeySelective(keywords);
    }

    public void deleteById(Integer id) {
        keywordsMapper.logicalDeleteByPrimaryKey(id);
    }
}
