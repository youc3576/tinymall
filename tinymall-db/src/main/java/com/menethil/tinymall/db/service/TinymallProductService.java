package com.menethil.tinymall.db.service;

import com.menethil.tinymall.db.dao.TinymallProductMapper;
import com.menethil.tinymall.db.domain.TinymallProduct;
import com.menethil.tinymall.db.domain.TinymallProductExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TinymallProductService {
    @Resource
    private TinymallProductMapper productMapper;

    public List<TinymallProduct> queryByGid(Integer gid) {
        TinymallProductExample example = new TinymallProductExample();
        example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return productMapper.selectByExample(example);
    }

    public TinymallProduct findById(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }

    public void updateById(TinymallProduct product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    public void deleteById(Integer id) {
        productMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(TinymallProduct product) {
        productMapper.insertSelective(product);
    }

    public int count() {
        TinymallProductExample example = new TinymallProductExample();
        example.or().andDeletedEqualTo(false);

        return (int)productMapper.countByExample(example);
    }

    public void deleteByGid(Integer gid) {
        TinymallProductExample example = new TinymallProductExample();
        example.or().andGoodsIdEqualTo(gid);
        productMapper.logicalDeleteByExample(example);
    }
}
