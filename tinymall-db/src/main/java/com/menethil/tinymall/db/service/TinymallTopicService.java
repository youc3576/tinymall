package com.menethil.tinymall.db.service;

import com.github.pagehelper.PageHelper;
import com.menethil.tinymall.db.dao.TinymallTopicMapper;
import com.menethil.tinymall.db.domain.TinymallTopic;
import com.menethil.tinymall.db.domain.TinymallTopicExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TinymallTopicService {
    @Resource
    private TinymallTopicMapper topicMapper;

    public List<TinymallTopic> queryList(int offset, int limit) {
        TinymallTopicExample example = new TinymallTopicExample();
        example.or().andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return topicMapper.selectByExampleWithBLOBs(example);
    }

    public int queryTotal() {
        TinymallTopicExample example = new TinymallTopicExample();
        example.or().andDeletedEqualTo(false);
        return (int)topicMapper.countByExample(example);
    }

    public TinymallTopic findById(Integer id) {
        TinymallTopicExample example = new TinymallTopicExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return topicMapper.selectOneByExampleWithBLOBs(example);
    }

    public List<TinymallTopic> queryRelatedList(Integer id, int offset, int limit) {
        TinymallTopicExample example = new TinymallTopicExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        List<TinymallTopic> topics = topicMapper.selectByExample(example);
        if(topics.size() == 0){
            return queryList(offset, limit);
        }
        TinymallTopic topic = topics.get(0);

        example = new TinymallTopicExample();
        example.or().andIdNotEqualTo(topic.getId()).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        List<TinymallTopic> relateds = topicMapper.selectByExampleWithBLOBs(example);
        if(relateds.size() != 0){
            return relateds;
        }

        return queryList(offset, limit);
    }

    public List<TinymallTopic> querySelective(String title, String subtitle, Integer page, Integer limit, String sort, String order) {
        TinymallTopicExample example = new TinymallTopicExample();
        TinymallTopicExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(title)){
            criteria.andTitleLike("%" + title + "%");
        }
        if(!StringUtils.isEmpty(subtitle)){
            criteria.andSubtitleLike("%" + subtitle + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return topicMapper.selectByExampleWithBLOBs(example);
    }

    public int countSelective(String title, String subtitle, Integer page, Integer size, String sort, String order) {
        TinymallTopicExample example = new TinymallTopicExample();
        TinymallTopicExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(title)){
            criteria.andTitleLike("%" + title + "%");
        }
        if(!StringUtils.isEmpty(subtitle)){
            criteria.andSubtitleLike("%" + subtitle + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)topicMapper.countByExample(example);
    }

    public void updateById(TinymallTopic topic) {
        TinymallTopicExample example = new TinymallTopicExample();
        example.or().andIdEqualTo(topic.getId());
        topicMapper.updateByExampleWithBLOBs(topic, example);
    }

    public void deleteById(Integer id) {
        topicMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(TinymallTopic topic) {
        topicMapper.insertSelective(topic);
    }


}
