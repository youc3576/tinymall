package com.menethil.tinymall.db.service;

import com.github.pagehelper.PageHelper;
import com.menethil.tinymall.db.dao.TinymallCartMapper;
import com.menethil.tinymall.db.domain.TinymallCart;
import com.menethil.tinymall.db.domain.TinymallCartExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TinymallCartService {
    @Resource
    private TinymallCartMapper cartMapper;

    public TinymallCart queryExist(Integer goodsId, Integer productId, Integer userId) {
        TinymallCartExample example = new TinymallCartExample();
        example.or().andGoodsIdEqualTo(goodsId).andProductIdEqualTo(productId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return cartMapper.selectOneByExample(example);
    }

    public void add(TinymallCart cart) {
        cartMapper.insertSelective(cart);
    }

    public void update(TinymallCart cart) {
        cartMapper.updateByPrimaryKey(cart);
    }

    public List<TinymallCart> queryByUid(int userId) {
        TinymallCartExample example = new TinymallCartExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return cartMapper.selectByExample(example);
    }


    public List<TinymallCart> queryByUidAndChecked(Integer userId) {
        TinymallCartExample example = new TinymallCartExample();
        example.or().andUserIdEqualTo(userId).andCheckedEqualTo(true).andDeletedEqualTo(false);
        return cartMapper.selectByExample(example);
    }

    public List<TinymallCart> queryByUidAndSid(int userId, String sessionId) {
        TinymallCartExample example = new TinymallCartExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return cartMapper.selectByExample(example);
    }

    public int delete(List<Integer> productIdList, int userId) {
        TinymallCartExample example = new TinymallCartExample();
        example.or().andUserIdEqualTo(userId).andProductIdIn(productIdList);
        return cartMapper.logicalDeleteByExample(example);
    }

    public TinymallCart findById(Integer id) {
        return cartMapper.selectByPrimaryKey(id);
    }

    public int updateCheck(Integer userId, List<Integer> idsList, Boolean checked) {
        TinymallCartExample example = new TinymallCartExample();
        example.or().andUserIdEqualTo(userId).andProductIdIn(idsList).andDeletedEqualTo(false);
        TinymallCart cart = new TinymallCart();
        cart.setChecked(checked);
        return cartMapper.updateByExampleSelective(cart, example);
    }

    public void clearGoods(Integer userId) {
        TinymallCartExample example = new TinymallCartExample();
        example.or().andUserIdEqualTo(userId).andCheckedEqualTo(true);
        TinymallCart cart = new TinymallCart();
        cart.setDeleted(true);
        cartMapper.updateByExampleSelective(cart, example);
    }

    public List<TinymallCart> querySelective(Integer userId, Integer goodsId, Integer page, Integer limit, String sort, String order) {
        TinymallCartExample example = new TinymallCartExample();
        TinymallCartExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(goodsId)){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return cartMapper.selectByExample(example);
    }

    public int countSelective(Integer userId, Integer goodsId, Integer page, Integer limit, String sort, String order) {
        TinymallCartExample example = new TinymallCartExample();
        TinymallCartExample.Criteria criteria = example.createCriteria();

        if(userId != null){
            criteria.andUserIdEqualTo(userId);
        }
        if(goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);

        return (int)cartMapper.countByExample(example);
    }

    public void deleteById(Integer id) {
        cartMapper.logicalDeleteByPrimaryKey(id);
    }
}
