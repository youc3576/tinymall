package com.menethil.tinymall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.menethil.tinymall.admin.annotation.LoginAdmin;
import com.menethil.tinymall.core.util.ResponseUtil;
import com.menethil.tinymall.db.service.TinymallGoodsService;
import com.menethil.tinymall.db.service.TinymallOrderService;
import com.menethil.tinymall.db.service.TinymallProductService;
import com.menethil.tinymall.db.service.TinymallUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashbordController {
    private final Log logger = LogFactory.getLog(AdminDashbordController.class);

    @Autowired
    private TinymallUserService userService;
    @Autowired
    private TinymallGoodsService goodsService;
    @Autowired
    private TinymallProductService productService;
    @Autowired
    private TinymallOrderService orderService;

    @GetMapping("")
    public Object info(@LoginAdmin Integer adminId){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        int userTotal = userService.count();
        int goodsTotal = goodsService.count();
        int productTotal = productService.count();
        int orderTotal = orderService.count();
        Map<String, Integer> data = new HashMap<>();
        data.put("userTotal", userTotal);
        data.put("goodsTotal", goodsTotal);
        data.put("productTotal", productTotal);
        data.put("orderTotal", orderTotal);

        return ResponseUtil.ok(data);
    }

}
