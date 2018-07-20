package com.menethil.tinymall.admin.web;

import com.menethil.tinymall.admin.annotation.LoginAdmin;
import com.menethil.tinymall.core.util.ResponseUtil;
import com.menethil.tinymall.db.domain.TinymallKeyword;
import com.menethil.tinymall.db.service.TinymallKeywordService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/keyword")
public class AdminKeywordController {
    private final Log logger = LogFactory.getLog(AdminKeywordController.class);

    @Autowired
    private TinymallKeywordService keywordService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       String keyword, String url,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        List<TinymallKeyword> brandList = keywordService.querySelective(keyword, url, page, limit, sort, order);
        int total = keywordService.countSelective(keyword, url, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", brandList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody TinymallKeyword keywords){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        keywords.setAddTime(LocalDateTime.now());
        keywordService.add(keywords);
        return ResponseUtil.ok(keywords);
    }

    @GetMapping("/read")
    public Object read(@LoginAdmin Integer adminId, Integer id){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        if(id == null){
            return ResponseUtil.badArgument();
        }

        TinymallKeyword brand = keywordService.findById(id);
        return ResponseUtil.ok(brand);
    }

    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody TinymallKeyword keywords){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        keywordService.updateById(keywords);
        return ResponseUtil.ok(keywords);
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin Integer adminId, @RequestBody TinymallKeyword brand){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        keywordService.deleteById(brand.getId());
        return ResponseUtil.ok();
    }

}
