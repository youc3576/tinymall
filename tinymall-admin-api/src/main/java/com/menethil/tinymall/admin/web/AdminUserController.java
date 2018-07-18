package com.menethil.tinymall.admin.web;

import com.github.pagehelper.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.menethil.tinymall.admin.annotation.LoginAdmin;
import com.menethil.tinymall.core.util.ResponseUtil;
import com.menethil.tinymall.db.domain.TinymallUser;
import com.menethil.tinymall.db.service.TinymallUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {
    private final Log logger = LogFactory.getLog(AdminUserController.class);

    @Autowired
    private TinymallUserService userService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       String username, String mobile,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        List<TinymallUser> userList = userService.querySelective(username, mobile, page, limit, sort, order);
        int total = userService.countSeletive(username, mobile, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", userList);

        return ResponseUtil.ok(data);
    }

    @GetMapping("/username")
    public Object username(String username){
        if(StringUtil.isEmpty(username)){
            return ResponseUtil.badArgument();
        }

        int total = userService.countSeletive(username, null, null, null, null, null);
        if(total == 0){
            return ResponseUtil.ok("不存在");
        }
        return ResponseUtil.ok("已存在");
    }


    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody TinymallUser user){
        logger.debug(user);
        user.setAddTime(LocalDateTime.now());
        userService.add(user);
        return ResponseUtil.ok(user);
    }

    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody TinymallUser user){
        logger.debug(user);

        userService.update(user);
        return ResponseUtil.ok(user);
    }
}
