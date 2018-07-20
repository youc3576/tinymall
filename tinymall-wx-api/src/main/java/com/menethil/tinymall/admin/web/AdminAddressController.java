package com.menethil.tinymall.admin.web;

import com.menethil.tinymall.admin.annotation.LoginAdmin;
import com.menethil.tinymall.core.util.RegexUtil;
import com.menethil.tinymall.core.util.ResponseUtil;
import com.menethil.tinymall.db.domain.TinymallAddress;
import com.menethil.tinymall.db.service.TinymallAddressService;
import com.menethil.tinymall.db.service.TinymallRegionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/address")
public class AdminAddressController {
    private final Log logger = LogFactory.getLog(AdminAddressController.class);

    @Autowired
    private TinymallAddressService addressService;
    @Autowired
    private TinymallRegionService regionService;

    private Map<String, Object> toVo (TinymallAddress address){
        Map<String, Object> addressVo = new HashMap<>();
        addressVo.put("id", address.getId());
        addressVo.put("userId", address.getUserId());
        addressVo.put("name", address.getName());
        addressVo.put("mobile", address.getMobile());
        addressVo.put("isDefault", address.getIsDefault());
        addressVo.put("provinceId", address.getProvinceId());
        addressVo.put("cityId", address.getCityId());
        addressVo.put("areaId", address.getAreaId());
        addressVo.put("address", address.getAddress());
        String province = regionService.findById(address.getProvinceId()).getName();
        String city = regionService.findById(address.getCityId()).getName();
        String area = regionService.findById(address.getAreaId()).getName();
        addressVo.put("province", province);
        addressVo.put("city", city);
        addressVo.put("area", area);
        return addressVo;
    }

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       Integer userId, String name,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        List<TinymallAddress> addressList = addressService.querySelective(userId, name, page, limit, sort, order);
        int total = addressService.countSelective(userId, name, page, limit, sort, order);

        List<Map<String, Object>> addressVoList = new ArrayList<>(addressList.size());
        for(TinymallAddress address : addressList){
            Map<String, Object> addressVo = toVo(address);
            addressVoList.add(addressVo);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", addressVoList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody TinymallAddress address){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        String mobile = address.getMobile();
        if(!RegexUtil.isMobileExact(mobile)){
            return ResponseUtil.fail(403, "手机号格式不正确");
        }

        address.setAddTime(LocalDateTime.now());
        addressService.add(address);

        Map<String, Object> addressVo = toVo(address);
        return ResponseUtil.ok(addressVo);
    }

    @GetMapping("/read")
    public Object read(@LoginAdmin Integer adminId, Integer addressId){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        TinymallAddress address = addressService.findById(addressId);
        Map<String, Object> addressVo = toVo(address);
        return ResponseUtil.ok(addressVo);
    }

    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody TinymallAddress address){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        addressService.updateById(address);
        Map<String, Object> addressVo = toVo(address);
        return ResponseUtil.ok(addressVo);
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin Integer adminId, @RequestBody TinymallAddress address){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        addressService.delete(address.getId());
        return ResponseUtil.ok();
    }

}
