package com.menethil.tinymall.db.service;

import com.github.pagehelper.PageHelper;
import com.menethil.tinymall.db.dao.TinymallAdminMapper;
import com.menethil.tinymall.db.domain.TinymallAdmin;
import com.menethil.tinymall.db.domain.TinymallAdmin.Column;
import com.menethil.tinymall.db.domain.TinymallAdminExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TinymallAdminService {
    @Resource
    private TinymallAdminMapper adminMapper;

    public List<TinymallAdmin> findAdmin(String username) {
        TinymallAdminExample example = new TinymallAdminExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return adminMapper.selectByExample(example);
    }

    private final Column[] result = new Column[]{Column.id, Column.username, Column.avatar};
    public List<TinymallAdmin> querySelective(String username, Integer page, Integer limit, String sort, String order) {
        TinymallAdminExample example = new TinymallAdminExample();
        TinymallAdminExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(username)){
            criteria.andUsernameLike("%" + username + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        
        PageHelper.startPage(page, limit);
        return adminMapper.selectByExampleSelective(example, result);
    }

    public int countSelective(String username, Integer page, Integer size, String sort, String order) {
        TinymallAdminExample example = new TinymallAdminExample();
        TinymallAdminExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(username)){
            criteria.andUsernameLike("%" + username + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)adminMapper.countByExample(example);
    }

    public void updateById(TinymallAdmin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    public void deleteById(Integer id) {
        adminMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(TinymallAdmin admin) {
        adminMapper.insertSelective(admin);
    }

    public TinymallAdmin findById(Integer id) {
        return adminMapper.selectByPrimaryKeySelective(id, result);
    }
}
