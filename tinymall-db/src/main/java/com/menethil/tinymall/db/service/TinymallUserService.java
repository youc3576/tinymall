package com.menethil.tinymall.db.service;

import com.github.pagehelper.PageHelper;
import com.menethil.tinymall.db.dao.TinymallUserMapper;
import com.menethil.tinymall.db.domain.TinymallUser;
import com.menethil.tinymall.db.domain.TinymallUserExample;
import com.menethil.tinymall.db.domain.TinymallUserVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TinymallUserService {
    @Resource
    private TinymallUserMapper userMapper;

    public TinymallUser findById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public TinymallUserVo findUserVoById(Integer userId) {
        TinymallUser user = findById(userId);
        TinymallUserVo userVo = new TinymallUserVo();
        userVo.setNickname(user.getNickname());
        userVo.setAvatar(user.getAvatar());
        return userVo;
    }

    public TinymallUser queryByOid(String openId) {
        TinymallUserExample example = new TinymallUserExample();
        example.or().andWeixinOpenidEqualTo(openId).andDeletedEqualTo(false);
        return userMapper.selectOneByExample(example);
    }

    public void add(TinymallUser user) {
        userMapper.insertSelective(user);
    }

    public void update(TinymallUser user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    public List<TinymallUser> querySelective(String username, String mobile, Integer page, Integer size, String sort, String order) {
        TinymallUserExample example = new TinymallUserExample();
        TinymallUserExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(username)){
            criteria.andUsernameLike("%" + username + "%");
        }
        if(!StringUtils.isEmpty(mobile)){
            criteria.andMobileEqualTo(mobile);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return userMapper.selectByExample(example);
    }

    public int countSeletive(String username, String mobile, Integer page, Integer size, String sort, String order) {
        TinymallUserExample example = new TinymallUserExample();
        TinymallUserExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(username)){
            criteria.andUsernameLike("%" + username + "%");
        }
        if(!StringUtils.isEmpty(mobile)){
            criteria.andMobileEqualTo(mobile);
        }
        criteria.andDeletedEqualTo(false);

        return (int) userMapper.countByExample(example);
    }

    public int count() {
        TinymallUserExample example = new TinymallUserExample();
        example.or().andDeletedEqualTo(false);

        return (int)userMapper.countByExample(example);
    }

    public List<TinymallUser> queryByUsername(String username) {
        TinymallUserExample example = new TinymallUserExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public List<TinymallUser> queryByMobile(String mobile) {
        TinymallUserExample example = new TinymallUserExample();
        example.or().andMobileEqualTo(mobile).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        userMapper.logicalDeleteByPrimaryKey(id);
    }
}
