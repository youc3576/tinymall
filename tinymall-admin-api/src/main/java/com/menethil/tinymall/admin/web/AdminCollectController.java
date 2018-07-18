package com.menethil.tinymall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.menethil.tinymall.admin.annotation.LoginAdmin;
import com.menethil.tinymall.core.util.ResponseUtil;
import com.menethil.tinymall.db.domain.TinymallCollect;
import com.menethil.tinymall.db.service.TinymallCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/collect")
public class AdminCollectController {
    private final Log logger = LogFactory.getLog(AdminCollectController.class);

    @Autowired
    private TinymallCollectService collectService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       String userId, String valueId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        List<TinymallCollect> collectList = collectService.querySelective(userId, valueId, page, limit, sort, order);
        int total = collectService.countSelective(userId, valueId, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", collectList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody TinymallCollect collect){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        return ResponseUtil.unsupport();
    }

    @GetMapping("/read")
    public Object read(@LoginAdmin Integer adminId, Integer id){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        if(id == null){
            return ResponseUtil.badArgument();
        }

        TinymallCollect collect = collectService.findById(id);
        return ResponseUtil.ok(collect);
    }

    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody TinymallCollect collect){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        collectService.updateById(collect);
        return ResponseUtil.ok();
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin Integer adminId, @RequestBody TinymallCollect collect){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        collectService.deleteById(collect.getId());
        return ResponseUtil.ok();
    }

}
