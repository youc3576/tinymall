package com.menethil.tinymall.db.service;

import com.menethil.tinymall.db.dao.TinymallGoodsAttributeMapper;
import com.menethil.tinymall.db.domain.TinymallGoodsAttribute;
import com.menethil.tinymall.db.domain.TinymallGoodsAttributeExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TinymallGoodsAttributeService {
    @Resource
    private TinymallGoodsAttributeMapper goodsAttributeMapper;

    public List<TinymallGoodsAttribute> queryByGid(Integer goodsId) {
        TinymallGoodsAttributeExample example = new TinymallGoodsAttributeExample();
        example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return goodsAttributeMapper.selectByExample(example);
    }

    public void updateById(TinymallGoodsAttribute goodsAttribute) {
        goodsAttributeMapper.updateByPrimaryKeySelective(goodsAttribute);
    }

    public void deleteById(Integer id) {
        goodsAttributeMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(TinymallGoodsAttribute goodsAttribute) {
        goodsAttributeMapper.insertSelective(goodsAttribute);
    }

    public TinymallGoodsAttribute findById(Integer id) {
        return goodsAttributeMapper.selectByPrimaryKey(id);
    }

    public void deleteByGid(Integer gid) {
        TinymallGoodsAttributeExample example = new TinymallGoodsAttributeExample();
        example.or().andGoodsIdEqualTo(gid);
        goodsAttributeMapper.logicalDeleteByExample(example);
    }
}
