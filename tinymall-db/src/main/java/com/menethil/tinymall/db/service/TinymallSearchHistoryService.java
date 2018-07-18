package com.menethil.tinymall.db.service;

import com.github.pagehelper.PageHelper;
import com.menethil.tinymall.db.dao.TinymallSearchHistoryMapper;
import com.menethil.tinymall.db.domain.TinymallSearchHistory;
import com.menethil.tinymall.db.domain.TinymallSearchHistoryExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TinymallSearchHistoryService {
    @Resource
    private TinymallSearchHistoryMapper searchHistoryMapper;

    public void save(TinymallSearchHistory searchHistory) {
        searchHistoryMapper.insertSelective(searchHistory);
    }

    public List<TinymallSearchHistory> queryByUid(int uid) {
        TinymallSearchHistoryExample example = new TinymallSearchHistoryExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        example.setDistinct(true);
        return searchHistoryMapper.selectByExampleSelective(example, TinymallSearchHistory.Column.keyword);
    }

    public void deleteByUid(int uid) {
        TinymallSearchHistoryExample example = new TinymallSearchHistoryExample();
        example.or().andUserIdEqualTo(uid);
        searchHistoryMapper.logicalDeleteByExample(example);
    }

    public void deleteById(Integer id) {
        TinymallSearchHistory searchHistory = searchHistoryMapper.selectByPrimaryKey(id);
        if(searchHistory == null){
            return;
        }
        searchHistory.setDeleted(true);
        searchHistoryMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(TinymallSearchHistory searchHistory) {
        searchHistoryMapper.insertSelective(searchHistory);
    }

    public List<TinymallSearchHistory> querySelective(String userId, String keyword, Integer page, Integer size, String sort, String order) {
        TinymallSearchHistoryExample example = new TinymallSearchHistoryExample();
        TinymallSearchHistoryExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(keyword)){
            criteria.andKeywordLike("%" + keyword + "%" );
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return searchHistoryMapper.selectByExample(example);
    }

    public int countSelective(String userId, String keyword, Integer page, Integer size, String sort, String order) {
        TinymallSearchHistoryExample example = new TinymallSearchHistoryExample();
        TinymallSearchHistoryExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(keyword)){
            criteria.andKeywordLike("%" + keyword + "%" );
        }
        criteria.andDeletedEqualTo(false);

        return (int)searchHistoryMapper.countByExample(example);
    }

    public void updateById(TinymallSearchHistory collect) {
        searchHistoryMapper.updateByPrimaryKeySelective(collect);
    }

    public TinymallSearchHistory findById(Integer id) {
        return searchHistoryMapper.selectByPrimaryKey(id);
    }
}
