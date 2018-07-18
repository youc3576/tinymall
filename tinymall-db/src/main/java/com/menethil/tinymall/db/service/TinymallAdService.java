package com.menethil.tinymall.db.service;

import com.github.pagehelper.PageHelper;
import com.menethil.tinymall.db.dao.TinymallAdMapper;
import com.menethil.tinymall.db.domain.TinymallAd;
import com.menethil.tinymall.db.domain.TinymallAdExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TinymallAdService {
    @Resource
    private TinymallAdMapper adMapper;

    public List<TinymallAd> queryIndex() {
        TinymallAdExample example = new TinymallAdExample();
        example.or().andPositionEqualTo((byte)1).andDeletedEqualTo(false);
        return adMapper.selectByExample(example);
    }

    public List<TinymallAd> querySelective(String name, String content, Integer page, Integer limit, String sort, String order) {
        TinymallAdExample example = new TinymallAdExample();
        TinymallAdExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isEmpty(content)){
            criteria.andContentLike("%" + content + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return adMapper.selectByExample(example);
    }

    public int countSelective(String name, String content, Integer page, Integer size, String sort, String order) {
        TinymallAdExample example = new TinymallAdExample();
        TinymallAdExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isEmpty(content)){
            criteria.andContentLike("%" + content + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)adMapper.countByExample(example);
    }

    public void updateById(TinymallAd ad) {
        adMapper.updateByPrimaryKeySelective(ad);
    }

    public void deleteById(Integer id) {
        adMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(TinymallAd ad) {
        adMapper.insertSelective(ad);
    }

    public TinymallAd findById(Integer id) {
        return adMapper.selectByPrimaryKey(id);
    }
}
