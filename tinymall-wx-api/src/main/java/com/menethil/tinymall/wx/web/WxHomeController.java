package com.menethil.tinymall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.menethil.tinymall.core.util.ResponseUtil;
import com.menethil.tinymall.db.domain.*;
import com.menethil.tinymall.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/home")
public class WxHomeController {
    private final Log logger = LogFactory.getLog(WxHomeController.class);

    @Autowired
    private TinymallAdService adService;
    @Autowired
    private TinymallGoodsService goodsService;
    @Autowired
    private TinymallBrandService brandService;
    @Autowired
    private TinymallTopicService topicService;
    @Autowired
    private TinymallCategoryService categoryService;
    @Autowired
    private TinymallCartService cartService;

    /**
     * app首页
     *
     * @return app首页相关信息
     *   成功则
     *  {
     *      errno: 0,
     *      errmsg: '成功',
     *      data:
     *          {
     *              banner: xxx,
     *              channel: xxx,
     *              newGoodsList: xxx,
     *              hotGoodsList: xxx,
     *              topicList: xxx,
     *              floorGoodsList: xxx
     *          }
     *  }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @GetMapping("/index")
    public Object index() {
        Map<String, Object> data = new HashMap<>();

//        List<TinymallAd> banner = adService.queryIndex();
//        data.put("banner", banner);

//        List<TinymallCategory> channel = categoryService.queryChannel();
//        data.put("channel", channel);

        List<TinymallGoods> newGoods = goodsService.queryByNew(0, 4);
        data.put("newGoodsList", newGoods);

        List<TinymallGoods> hotGoods = goodsService.queryByHot(0, 5);
        data.put("hotGoodsList", hotGoods);

//        List<TinymallBrand> brandList = brandService.query(0,4);
//        data.put("brandList", brandList);

        List<TinymallTopic> topicList = topicService.queryList(0, 4);
        data.put("topicList", topicList);

//        List<Map> categoryList = new ArrayList<>();
//        List<TinymallCategory> catL1List = categoryService.queryL1WithoutRecommend(0, 6);
//        for (TinymallCategory catL1 : catL1List) {
//            List<TinymallCategory> catL2List = categoryService.queryByPid(catL1.getId());
//            List<Integer> l2List = new ArrayList<>();
//            for (TinymallCategory catL2 : catL2List) {
//                l2List.add(catL2.getId());
//            }
//
//            List<TinymallGoods> categoryGoods = null;
//            if(l2List.size() == 0){
//                categoryGoods = new ArrayList<>();
//            }
//            else{
//                categoryGoods = goodsService.queryByCategory(l2List, 0, 5);
//            }
//
//            Map catGoods = new HashMap();
//            catGoods.put("id", catL1.getId());
//            catGoods.put("name", catL1.getName());
//            catGoods.put("goodsList", categoryGoods);
//            categoryList.add(catGoods);
//        }
//        data.put("floorGoodsList", categoryList);

        return ResponseUtil.ok(data);
    }
}