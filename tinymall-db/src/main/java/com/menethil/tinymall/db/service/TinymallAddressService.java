package com.menethil.tinymall.db.service;

import com.github.pagehelper.PageHelper;
import com.menethil.tinymall.db.dao.TinymallAddressMapper;
import com.menethil.tinymall.db.domain.TinymallAddress;
import com.menethil.tinymall.db.domain.TinymallAddressExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TinymallAddressService {
    @Resource
    private TinymallAddressMapper addressMapper;

    public List<TinymallAddress> queryByUid(Integer uid) {
        TinymallAddressExample example = new TinymallAddressExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        return addressMapper.selectByExample(example);
    }

    public TinymallAddress findById(Integer id) {
        return addressMapper.selectByPrimaryKey(id);
    }

    public int add(TinymallAddress address) {
        return addressMapper.insertSelective(address);
    }

    public int update(TinymallAddress address) {
        return addressMapper.updateByPrimaryKeySelective(address);
    }

    public void delete(Integer id) {
        addressMapper.logicalDeleteByPrimaryKey(id);
    }

    public TinymallAddress findDefault(Integer userId) {
        TinymallAddressExample example = new TinymallAddressExample();
        example.or().andUserIdEqualTo(userId).andIsDefaultEqualTo(true).andDeletedEqualTo(false);
        return addressMapper.selectOneByExample(example);
    }

    public void resetDefault(Integer userId) {
        TinymallAddress address = new TinymallAddress();
        address.setIsDefault(false);
        TinymallAddressExample example = new TinymallAddressExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        addressMapper.updateByExampleSelective(address, example);
    }

    public List<TinymallAddress> querySelective(Integer userId, String name, Integer page, Integer limit, String sort, String order) {
        TinymallAddressExample example = new TinymallAddressExample();
        TinymallAddressExample.Criteria criteria = example.createCriteria();

        if(userId !=  null){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return addressMapper.selectByExample(example);
    }

    public int countSelective(Integer userId, String name, Integer page, Integer limit, String sort, String order) {
        TinymallAddressExample example = new TinymallAddressExample();
        TinymallAddressExample.Criteria criteria = example.createCriteria();

        if(userId !=  null){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)addressMapper.countByExample(example);
    }

    public void updateById(TinymallAddress address) {
        addressMapper.updateByPrimaryKeySelective(address);
    }
}
