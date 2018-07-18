package com.menethil.tinymall.db.service;

import com.github.pagehelper.PageHelper;
import com.menethil.tinymall.db.dao.TinymallGoodsMapper;
import com.menethil.tinymall.db.domain.TinymallGoods;
import com.menethil.tinymall.db.domain.TinymallGoods.Column;
import com.menethil.tinymall.db.domain.TinymallGoodsExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TinymallGoodsService {
    @Resource
    private TinymallGoodsMapper goodsMapper;

    public List<TinymallGoods> queryByHot(int offset, int limit) {
        TinymallGoodsExample example = new TinymallGoodsExample();
        example.or().andIsHotEqualTo(true).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return goodsMapper.selectByExample(example);
    }

    public List<TinymallGoods> queryByNew(int offset, int limit) {
        TinymallGoodsExample example = new TinymallGoodsExample();
        example.or().andIsNewEqualTo(true).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return goodsMapper.selectByExample(example);
    }

    public List<TinymallGoods> queryByCategory(List<Integer> catList, int offset, int limit) {
        TinymallGoodsExample example = new TinymallGoodsExample();
        example.or().andCategoryIdIn(catList).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return goodsMapper.selectByExample(example);
    }

    public int countByCategory(List<Integer> catList, int offset, int limit) {
        TinymallGoodsExample example = new TinymallGoodsExample();
        example.or().andCategoryIdIn(catList).andDeletedEqualTo(false);
        return (int)goodsMapper.countByExample(example);
    }

    public List<TinymallGoods> queryByCategory(Integer catId, int offset, int limit) {
        TinymallGoodsExample example = new TinymallGoodsExample();
        example.or().andCategoryIdEqualTo(catId).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return goodsMapper.selectByExample(example);
    }

    public int countByCategory(Integer catId, Integer page, Integer size) {
        TinymallGoodsExample example = new TinymallGoodsExample();
        example.or().andCategoryIdEqualTo(catId).andDeletedEqualTo(false);
        return (int)goodsMapper.countByExample(example);
    }

    public List<TinymallGoods> querySelective(Integer catId, Integer brandId, String keyword, Boolean isHot, Boolean isNew, Integer offset, Integer limit, String sort, String order) {
        TinymallGoodsExample example = new TinymallGoodsExample();
        TinymallGoodsExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(catId) && catId != 0){
            criteria.andCategoryIdEqualTo(catId);
        }
        if(!StringUtils.isEmpty(brandId)){
            criteria.andBrandIdEqualTo(brandId);
        }
        if(!StringUtils.isEmpty(isNew)){
            criteria.andIsNewEqualTo(isNew);
        }
        if(!StringUtils.isEmpty(isHot)){
            criteria.andIsHotEqualTo(isHot);
        }
        if(!StringUtils.isEmpty(keyword)){
            criteria.andKeywordsLike("%" + keyword + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        if(!StringUtils.isEmpty(limit) && !StringUtils.isEmpty(offset)) {
            PageHelper.startPage(offset, limit);
        }

        Column[] columns = new Column[]{Column.id, Column.name, Column.picUrl, Column.retailPrice};
        return goodsMapper.selectByExampleSelective(example ,columns);
    }

    public int countSelective(Integer catId, Integer brandId, String keyword, Boolean isHot, Boolean isNew, Integer offset, Integer limit, String sort, String order) {
        TinymallGoodsExample example = new TinymallGoodsExample();
        TinymallGoodsExample.Criteria criteria = example.createCriteria();

        if(catId != null){
            criteria.andCategoryIdEqualTo(catId);
        }
        if(brandId != null){
            criteria.andBrandIdEqualTo(brandId);
        }
        if(isNew != null){
            criteria.andIsNewEqualTo(isNew);
        }
        if(isHot != null){
            criteria.andIsHotEqualTo(isHot);
        }
        if(keyword != null){
            criteria.andKeywordsLike("%" + keyword + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)goodsMapper.countByExample(example);
    }

    public TinymallGoods findById(Integer id) {
        TinymallGoodsExample example = new TinymallGoodsExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return goodsMapper.selectOneByExampleWithBLOBs(example);
    }


    public List<TinymallGoods> queryByIds(List<Integer> relatedGoodsIds) {
        TinymallGoodsExample example = new TinymallGoodsExample();
        example.or().andIdIn(relatedGoodsIds).andDeletedEqualTo(false);
        return goodsMapper.selectByExampleWithBLOBs(example);
    }

    public Integer queryOnSale() {
        TinymallGoodsExample example = new TinymallGoodsExample();
        example.or().andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return (int)goodsMapper.countByExample(example);
    }

    public List<TinymallGoods> querySelective(String goodsSn, String name, Integer page, Integer size, String sort, String order) {
        TinymallGoodsExample example = new TinymallGoodsExample();
        TinymallGoodsExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(goodsSn)){
            criteria.andGoodsSnEqualTo(goodsSn);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return goodsMapper.selectByExampleWithBLOBs(example);
    }

    public int countSelective(String goodsSn, String name, Integer page, Integer size, String sort, String order) {
        TinymallGoodsExample example = new TinymallGoodsExample();
        TinymallGoodsExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(goodsSn)){
            criteria.andGoodsSnEqualTo(goodsSn);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)goodsMapper.countByExample(example);
    }

    public void updateById(TinymallGoods goods) {
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    public void deleteById(Integer id) {
        goodsMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(TinymallGoods goods) {
        goodsMapper.insertSelective(goods);
    }

    public int count() {
        TinymallGoodsExample example = new TinymallGoodsExample();
        example.or().andDeletedEqualTo(false);
        return (int)goodsMapper.countByExample(example);
    }

    public List<Integer> getCatIds(Integer brandId, String keyword, Boolean isHot, Boolean isNew) {
        TinymallGoodsExample example = new TinymallGoodsExample();
        TinymallGoodsExample.Criteria criteria = example.createCriteria();

        if(brandId != null){
            criteria.andBrandIdEqualTo(brandId);
        }
        if(isNew != null){
            criteria.andIsNewEqualTo(isNew);
        }
        if(isHot != null){
            criteria.andIsHotEqualTo(isHot);
        }
        if(keyword != null){
            criteria.andKeywordsLike("%" + keyword + "%");
        }
        criteria.andDeletedEqualTo(false);

        List<TinymallGoods> goodsList = goodsMapper.selectByExampleSelective(example, Column.categoryId);
        List<Integer> cats = new ArrayList<Integer>();
        for(TinymallGoods goods : goodsList){
            cats.add(goods.getCategoryId());
        }
        return cats;
    }

    public boolean checkExistByName(String name) {
        TinymallGoodsExample example = new TinymallGoodsExample();
        example.or().andNameEqualTo(name).andDeletedEqualTo(false);
        return goodsMapper.countByExample(example) != 0;
    }
}