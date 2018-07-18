package com.menethil.tinymall.wx.web;


import cn.binarywang.wx.miniapp.api.WxMaService;
import com.menethil.tinymall.core.notify.TinymallNotifyService;
import com.menethil.tinymall.core.notify.util.ConfigUtil;
import com.menethil.tinymall.core.util.JacksonUtil;
import com.menethil.tinymall.core.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wx/test")
public class WxTestController {
    @Autowired
    private WxMaService wxService;

    @Autowired
    private TinymallNotifyService tinymallNotifyService;

    @RequestMapping("form")
    public Object login(@RequestBody String body, HttpServletRequest request) {
        String formId = JacksonUtil.parseString(body, "formId");
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        if (username == null || password == null || formId == null) {
            return ResponseUtil.badArgument();
        }

        try {
            tinymallNotifyService.notifyWXTemplate(wxService.getAccessToken(), "oMC-W5CuKnCU2cLwuNdVMbYZPxIo"
                    , formId, ConfigUtil.NotifyType.CAPTCHA, new String[]{"11111"});
            tinymallNotifyService.notifySMSTemplate("18650808121",ConfigUtil.NotifyType.PAY_SUCCEED,new String[]{"不知火舞"});
            tinymallNotifyService.notifyMailMessage("订单通知","不知火舞");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<Object, Object> result = new HashMap<>();
        result.put("formId", formId);
        result.put("username", username);
        result.put("password", password);
        return ResponseUtil.ok(result);
    }
}
