package com.menethil.tinymall.db.service;

import com.menethil.tinymall.db.dao.TinymallGoodsSpecificationMapper;
import com.menethil.tinymall.db.domain.TinymallGoodsSpecification;
import com.menethil.tinymall.db.domain.TinymallGoodsSpecificationExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TinymallGoodsSpecificationService {
    @Resource
    private TinymallGoodsSpecificationMapper goodsSpecificationMapper;

    public List<TinymallGoodsSpecification> queryByGid(Integer id) {
        TinymallGoodsSpecificationExample example = new TinymallGoodsSpecificationExample();
        example.or().andGoodsIdEqualTo(id).andDeletedEqualTo(false);
        return goodsSpecificationMapper.selectByExample(example);
    }

    public TinymallGoodsSpecification findById(Integer id) {
        return goodsSpecificationMapper.selectByPrimaryKey(id);
    }

    public void updateById(TinymallGoodsSpecification goodsSpecification) {
        goodsSpecificationMapper.updateByPrimaryKeySelective(goodsSpecification);
    }

    public void deleteById(Integer id) {
        goodsSpecificationMapper.logicalDeleteByPrimaryKey(id);
    }

    public void deleteByGid(Integer gid) {
        TinymallGoodsSpecificationExample example = new TinymallGoodsSpecificationExample();
        example.or().andGoodsIdEqualTo(gid);
        goodsSpecificationMapper.logicalDeleteByExample(example);
    }

    public void add(TinymallGoodsSpecification goodsSpecification) {
        goodsSpecificationMapper.insertSelective(goodsSpecification);
    }

    private class VO {
        private String name;
        private List<TinymallGoodsSpecification> valueList;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public List<TinymallGoodsSpecification> getValueList() {
            return valueList;
        }

        public void setValueList(List<TinymallGoodsSpecification> valueList) {
            this.valueList = valueList;
        }
    }

    /**
     * [
     *  {
     *      name: '',
     *      valueList: [ {}, {}]
     *  },
     *  {
     *      name: '',
     *      valueList: [ {}, {}]
     *  }
     *  ]
     *
     * @param id
     * @return
     */
    public Object getSpecificationVoList(Integer id) {
        List<TinymallGoodsSpecification> goodsSpecificationList = queryByGid(id);

        Map<String, VO> map = new HashMap<>();
        List<VO> specificationVoList = new ArrayList<>();

        for(TinymallGoodsSpecification goodsSpecification : goodsSpecificationList){
            String specification = goodsSpecification.getSpecification();
            VO goodsSpecificationVo = map.get(specification);
            if(goodsSpecificationVo == null){
                goodsSpecificationVo = new VO();
                goodsSpecificationVo.setName(specification);
                List<TinymallGoodsSpecification> valueList = new ArrayList<>();
                valueList.add(goodsSpecification);
                goodsSpecificationVo.setValueList(valueList);
                map.put(specification, goodsSpecificationVo);
                specificationVoList.add(goodsSpecificationVo);
            }
            else{
                List<TinymallGoodsSpecification> valueList = goodsSpecificationVo.getValueList();
                valueList.add(goodsSpecification);
            }
        }

        return specificationVoList;
    }

}
