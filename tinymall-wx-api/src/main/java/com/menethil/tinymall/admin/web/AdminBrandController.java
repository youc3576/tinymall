package com.menethil.tinymall.admin.web;

import com.menethil.tinymall.admin.annotation.LoginAdmin;
import com.menethil.tinymall.core.util.ResponseUtil;
import com.menethil.tinymall.db.domain.TinymallBrand;
import com.menethil.tinymall.db.service.TinymallBrandService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/brand")
public class AdminBrandController {
    private final Log logger = LogFactory.getLog(AdminBrandController.class);

    @Autowired
    private TinymallBrandService brandService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       String id, String name,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        List<TinymallBrand> brandList = brandService.querySelective(id, name, page, limit, sort, order);
        int total = brandService.countSelective(id, name, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", brandList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody TinymallBrand brand){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        brand.setAddTime(LocalDateTime.now());
        brandService.add(brand);
        return ResponseUtil.ok(brand);
    }

    @GetMapping("/read")
    public Object read(@LoginAdmin Integer adminId, Integer id){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        if(id == null){
            return ResponseUtil.badArgument();
        }

        TinymallBrand brand = brandService.findById(id);
        return ResponseUtil.ok(brand);
    }

    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody TinymallBrand brand){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        brandService.updateById(brand);
        return ResponseUtil.ok(brand);
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin Integer adminId, @RequestBody TinymallBrand brand){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        brandService.deleteById(brand.getId());
        return ResponseUtil.ok();
    }

}
