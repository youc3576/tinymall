package com.menethil.tinymall.db.service;

import com.menethil.tinymall.db.dao.TinymallOrderGoodsMapper;
import com.menethil.tinymall.db.domain.TinymallOrderGoods;
import com.menethil.tinymall.db.domain.TinymallOrderGoodsExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TinymallOrderGoodsService {
    @Resource
    private TinymallOrderGoodsMapper orderGoodsMapper;

    public int add(TinymallOrderGoods orderGoods) {
        return orderGoodsMapper.insertSelective(orderGoods);
    }

    public List<TinymallOrderGoods> queryByOid(Integer orderId) {
        TinymallOrderGoodsExample example = new TinymallOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        return orderGoodsMapper.selectByExample(example);
    }
    public List<TinymallOrderGoods> findByOidAndGid(Integer orderId, Integer goodsId) {
        TinymallOrderGoodsExample example = new TinymallOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return orderGoodsMapper.selectByExample(example);
    }

}
