package com.menethil.tinymall.admin.web;

import com.menethil.tinymall.admin.annotation.LoginAdmin;
import com.menethil.tinymall.admin.dao.GoodsAllinone;
import com.menethil.tinymall.admin.util.CatVo;
import com.menethil.tinymall.core.util.ResponseUtil;
import com.menethil.tinymall.db.domain.*;
import com.menethil.tinymall.db.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/goods")
public class AdminGoodsController {
    private final Log logger = LogFactory.getLog(AdminGoodsController.class);

    @Autowired
    private PlatformTransactionManager txManager;

    @Autowired
    private TinymallGoodsService goodsService;
    @Autowired
    private TinymallGoodsSpecificationService specificationService;
    @Autowired
    private TinymallGoodsAttributeService attributeService;
    @Autowired
    private TinymallProductService productService;
    @Autowired
    private TinymallCategoryService categoryService;
    @Autowired
    private TinymallBrandService brandService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       String goodsSn, String name,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        List<TinymallGoods> goodsList = goodsService.querySelective(goodsSn, name, page, limit, sort, order);
        int total = goodsService.countSelective(goodsSn, name, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", goodsList);

        return ResponseUtil.ok(data);
    }

    /*
     * TODO
     * 目前商品修改的逻辑是
     * 1. 更新tinymall_goods表
     * 2. 逻辑删除tinymall_goods_specification、tinymall_goods_attribute、tinymall_product
     * 3. 添加tinymall_goods_specification、tinymall_goods_attribute、tinymall_product
     *
     * 这里商品三个表的数据采用删除再跟新的策略是因为
     * 商品编辑页面，管理员可以添加删除商品规格、添加删除商品属性，因此这里仅仅更新表是不可能的，
     * 因此这里只能删除所有旧的数据，然后添加新的数据
     */
    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody GoodsAllinone goodsAllinone){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        TinymallGoods goods = goodsAllinone.getGoods();
        TinymallGoodsAttribute[] attributes = goodsAllinone.getAttributes();
        TinymallGoodsSpecification[] specifications = goodsAllinone.getSpecifications();
        TinymallProduct[] products = goodsAllinone.getProducts();

        // 开启事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        try {

            // 商品基本信息表tinymall_goods
            goodsService.updateById(goods);

            Integer gid = goods.getId();
            specificationService.deleteByGid(gid);
            attributeService.deleteByGid(gid);
            productService.deleteByGid(gid);

            // 商品规格表tinymall_goods_specification
            Map<String, Integer> specIds = new HashMap<>();
            for(TinymallGoodsSpecification specification : specifications){
                specification.setGoodsId(goods.getId());
                specification.setAddTime(LocalDateTime.now());
                specificationService.add(specification);
                specIds.put(specification.getValue(), specification.getId());
            }

            // 商品参数表tinymall_goods_attribute
            for(TinymallGoodsAttribute attribute : attributes){
                attribute.setGoodsId(goods.getId());
                attribute.setAddTime(LocalDateTime.now());
                attributeService.add(attribute);
            }

            // 商品货品表tinymall_product
            for(TinymallProduct product : products){
                product.setGoodsId(goods.getId());
                product.setAddTime(LocalDateTime.now());
                productService.add(product);
            }
        } catch (Exception ex) {
            txManager.rollback(status);
            logger.error("系统内部错误", ex);
        }
        txManager.commit(status);

        return ResponseUtil.ok();
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin Integer adminId, @RequestBody TinymallGoods goods){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        // 开启事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        try {

            Integer gid = goods.getId();
            goodsService.deleteById(gid);
            specificationService.deleteByGid(gid);
            attributeService.deleteByGid(gid);
            productService.deleteByGid(gid);
        } catch (Exception ex) {
            txManager.rollback(status);
            logger.error("系统内部错误", ex);
        }
        txManager.commit(status);
        return ResponseUtil.ok();
    }

    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody GoodsAllinone goodsAllinone){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        TinymallGoods goods = goodsAllinone.getGoods();
        TinymallGoodsAttribute[] attributes = goodsAllinone.getAttributes();
        TinymallGoodsSpecification[] specifications = goodsAllinone.getSpecifications();
        TinymallProduct[] products = goodsAllinone.getProducts();

        String name = goods.getName();
        if(goodsService.checkExistByName(name)){
            return ResponseUtil.fail(403, "商品名已经存在");
        }

        // 开启事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        try {

            // 商品基本信息表tinymall_goods
            goods.setAddTime(LocalDateTime.now());
            goodsService.add(goods);

            // 商品规格表tinymall_goods_specification
            Map<String, Integer> specIds = new HashMap<>();
            for(TinymallGoodsSpecification specification : specifications){
                specification.setGoodsId(goods.getId());
                specification.setAddTime(LocalDateTime.now());
                specificationService.add(specification);
                specIds.put(specification.getValue(), specification.getId());
            }

            // 商品参数表tinymall_goods_attribute
            for(TinymallGoodsAttribute attribute : attributes){
                attribute.setGoodsId(goods.getId());
                attribute.setAddTime(LocalDateTime.now());
                attributeService.add(attribute);
            }

            // 商品货品表tinymall_product
            for(TinymallProduct product : products){
                product.setGoodsId(goods.getId());
                product.setAddTime(LocalDateTime.now());
                productService.add(product);
            }
        } catch (Exception ex) {
            txManager.rollback(status);
            logger.error("系统内部错误", ex);
        }
        txManager.commit(status);

        return ResponseUtil.ok();
    }



    @GetMapping("/catAndBrand")
    public Object list2(@LoginAdmin Integer adminId) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }

        // http://element-cn.eleme.io/#/zh-CN/component/cascader
        // 管理员设置“所属分类”
        List<TinymallCategory> l1CatList = categoryService.queryL1();
        List<CatVo> categoryList = new ArrayList<>(l1CatList.size());

        for(TinymallCategory l1 : l1CatList){
            CatVo l1CatVo = new CatVo();
            l1CatVo.setValue(l1.getId());
            l1CatVo.setLabel(l1.getName());

            List<TinymallCategory> l2CatList = categoryService.queryByPid(l1.getId());
            List<CatVo> children = new ArrayList<>(l2CatList.size());
            for(TinymallCategory l2 : l2CatList) {
                CatVo l2CatVo = new CatVo();
                l2CatVo.setValue(l2.getId());
                l2CatVo.setLabel(l2.getName());
                children.add(l2CatVo);
            }
            l1CatVo.setChildren(children);

            categoryList.add(l1CatVo);
        }

        // http://element-cn.eleme.io/#/zh-CN/component/select
        // 管理员设置“所属品牌商”
        List<TinymallBrand> list = brandService.all();
        List<Map<String, Object>> brandList = new ArrayList<>(l1CatList.size());
        for(TinymallBrand brand : list){
            Map<String, Object> b = new HashMap<>(2);
            b.put("value", brand.getId());
            b.put("label", brand.getName());
            brandList.add(b);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("categoryList" ,categoryList);
        data.put("brandList", brandList);
        return ResponseUtil.ok(data);
    }

    @GetMapping("/detail")
    public Object detail(@LoginAdmin Integer adminId, Integer id) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }

        if (id == null) {
            return ResponseUtil.badArgument();
        }

        TinymallGoods goods = goodsService.findById(id);
        List<TinymallProduct> products = productService.queryByGid(id);
        List<TinymallGoodsSpecification> specifications = specificationService.queryByGid(id);
        List<TinymallGoodsAttribute> attributes = attributeService.queryByGid(id);

        Integer categoryId = goods.getCategoryId();
        TinymallCategory category = categoryService.findById(categoryId);
        Integer[] categoryIds = new Integer[]{};
        if (category != null) {
            Integer parentCategoryId = category.getPid();
            categoryIds = new Integer[] {parentCategoryId, categoryId};
        }

        Map<String, Object> data = new HashMap<>();
        data.put("goods" ,goods);
        data.put("specifications", specifications);
        data.put("products", products);
        data.put("attributes", attributes);
        data.put("categoryIds", categoryIds);

        return ResponseUtil.ok(data);
    }

}
