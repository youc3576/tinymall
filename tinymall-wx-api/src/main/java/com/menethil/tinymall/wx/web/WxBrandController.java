package com.menethil.tinymall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.menethil.tinymall.core.util.ResponseUtil;
import com.menethil.tinymall.db.domain.TinymallBrand;
import com.menethil.tinymall.db.service.TinymallBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/brand")
public class WxBrandController {
    private final Log logger = LogFactory.getLog(WxBrandController.class);

    @Autowired
    private TinymallBrandService brandService;

    /**
     * 品牌列表
     *
     * @param page 分页页数
     * @param size 分页大小
     * @return 品牌列表
     *   成功则
     *  {
     *      errno: 0,
     *      errmsg: '成功',
     *      data:
     *          {
     *              brandList: xxx,
     *              totalPages: xxx
     *          }
     *  }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @GetMapping("list")
    public Object list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "size", defaultValue = "10") Integer size) {

        List<TinymallBrand> brandList = brandService.query(page, size);
        int total = brandService.queryTotalCount();
        int totalPages = (int) Math.ceil((double) total / size);

        Map<String, Object> data = new HashMap();
        data.put("brandList", brandList);
        data.put("totalPages", totalPages);
        return ResponseUtil.ok(data);
    }

    /**
     * 品牌详情
     *
     * @param id 品牌ID
     * @return 品牌详情
     *   成功则
     *  {
     *      errno: 0,
     *      errmsg: '成功',
     *      data:
     *          {
     *              brand: xxx
     *          }
     *  }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @GetMapping("detail")
    public Object detail(Integer id) {
        if(id == null){
            return ResponseUtil.badArgument();
        }

        TinymallBrand entity = brandService.findById(id);
        if(entity == null){
            return ResponseUtil.badArgumentValue();
        }

        Map<String, Object> data = new HashMap();
        data.put("brand",entity);
        return ResponseUtil.ok(data);
    }
}