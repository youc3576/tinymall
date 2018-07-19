package com.menethil.tinymall.wx.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.menethil.tinymall.core.express.ExpressService;
import com.menethil.tinymall.core.express.dao.ExpressInfo;
import com.menethil.tinymall.core.util.JacksonUtil;
import com.menethil.tinymall.core.util.ResponseUtil;
import com.menethil.tinymall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 物流信息查询接口
 */
@RestController
@RequestMapping("/wx/express")
public class WxExpressController {

    @Autowired
    ExpressService expressService;

    @PostMapping("query")
    public Object query(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        String expCode = JacksonUtil.parseString(body, "expCode");
        String expNo = JacksonUtil.parseString(body, "expNo");

        try {
            String result = expressService.getOrderTracesByJson(expCode, expNo);

            ObjectMapper objMap = new ObjectMapper();
            ExpressInfo ei = objMap.readValue(result, ExpressInfo.class);

            Map<String, Object> data = new HashMap<>();
            data.put("expCode", ei.getLogisticCode());
            data.put("expNo", ei.getShipperCode());
            data.put("expName", expressService.getVendorName(expCode));
            data.put("Traces", ei.getTraces());

            return ResponseUtil.ok(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseUtil.badArgument();
    }
}
