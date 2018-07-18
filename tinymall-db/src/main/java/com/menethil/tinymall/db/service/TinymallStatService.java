package com.menethil.tinymall.db.service;

import com.menethil.tinymall.db.dao.StatMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TinymallStatService {
    @Resource
    private StatMapper statMapper;


    public List<Map> statUser() {
        return statMapper.statUser();
    }

    public List<Map> statOrder(){
        return statMapper.statOrder();
    }

    public List<Map> statGoods(){
        return statMapper.statGoods();
    }
}
