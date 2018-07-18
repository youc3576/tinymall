package com.menethil.tinymall.db.service;

import com.github.pagehelper.PageHelper;
import com.menethil.tinymall.db.dao.TinymallIssueMapper;
import com.menethil.tinymall.db.domain.TinymallIssue;
import com.menethil.tinymall.db.domain.TinymallIssueExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TinymallIssueService {
    @Resource
    private TinymallIssueMapper issueMapper;

    public List<TinymallIssue> query() {
        TinymallIssueExample example = new TinymallIssueExample();
        example.or().andDeletedEqualTo(false);
        return issueMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        issueMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(TinymallIssue issue) {
        issueMapper.insertSelective(issue);
    }

    public List<TinymallIssue> querySelective(String question, Integer page, Integer size, String sort, String order) {
        TinymallIssueExample example = new TinymallIssueExample();
        TinymallIssueExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(question)){
            criteria.andQuestionLike("%" + question + "%" );
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return issueMapper.selectByExample(example);
    }

    public int countSelective(String question, Integer page, Integer size, String sort, String order) {
        TinymallIssueExample example = new TinymallIssueExample();
        TinymallIssueExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(question)){
            criteria.andQuestionLike("%" + question + "%" );
        }
        criteria.andDeletedEqualTo(false);

        return (int)issueMapper.countByExample(example);
    }

    public void updateById(TinymallIssue issue) {
        issueMapper.updateByPrimaryKeySelective(issue);
    }

    public TinymallIssue findById(Integer id) {
        return issueMapper.selectByPrimaryKey(id);
    }
}
