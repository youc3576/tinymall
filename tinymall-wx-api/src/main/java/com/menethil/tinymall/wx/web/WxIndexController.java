package com.menethil.tinymall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.menethil.tinymall.core.util.ResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx/index")
public class WxIndexController {
    private final Log logger = LogFactory.getLog(WxIndexController.class);

    @RequestMapping("/index")
    public Object index(){
        return ResponseUtil.ok("hello world, this is wx service");
    }

}