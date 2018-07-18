package com.menethil.tinymall.wx.web;

import com.menethil.tinymall.core.store.TinymallStoreService;
import com.menethil.tinymall.core.util.ResponseUtil;
import com.menethil.tinymall.db.domain.TinymallStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/storage")
public class WXStorageController {

    @Autowired
    TinymallStoreService tinymallStoreService;
    @Value("${server.url}")
    private String address;
    @Value("${server.port}")
    private int port;

    private String getFetchUrl() {
        return address + ":" + port + "/wx/storage/fetch/";
    }

    @GetMapping("/list")
    public Object list(String key, String name,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order) {
        List<TinymallStorage> storageList = tinymallStoreService.querySelective(key, name, page, limit, sort, order);
        int total = tinymallStoreService.countSelective(key, name, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", storageList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestParam("file") MultipartFile file) {
        try {
            TinymallStorage tinymallStorage = tinymallStoreService.create(file, getFetchUrl());
            return ResponseUtil.ok(tinymallStorage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.badArgumentValue();
        }
    }

    @PostMapping("/read")
    public Object read(Integer id) {
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        TinymallStorage storageInfo = tinymallStoreService.read(id);
        return ResponseUtil.ok(storageInfo);
    }

    @PostMapping("/update")
    public Object update(@RequestBody TinymallStorage tinymallStorage) {
        tinymallStoreService.update(tinymallStorage);
        return ResponseUtil.ok(tinymallStorage);
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody TinymallStorage tinymallStorage) {
        tinymallStoreService.delete(tinymallStorage);
        return ResponseUtil.ok();
    }

    @GetMapping("/fetch/{key:.+}")
    public ResponseEntity<Resource> fetch(@PathVariable String key) {
        TinymallStorage tinymallStorage = tinymallStoreService.findByKey(key);
        if (key == null) {
            ResponseEntity.notFound();
        }
        String type = tinymallStorage.getType();
        MediaType mediaType = MediaType.parseMediaType(type);

        Resource file = tinymallStoreService.loadAsResource(key);
        if (file == null) {
            ResponseEntity.notFound();
        }
        return ResponseEntity.ok().contentType(mediaType).body(file);
    }

    @GetMapping("/download/{key:.+}")
    public ResponseEntity<Resource> download(@PathVariable String key) {
        TinymallStorage tinymallStorage = tinymallStoreService.findByKey(key);
        if (key == null) {
            ResponseEntity.notFound();
        }
        String type = tinymallStorage.getType();
        MediaType mediaType = MediaType.parseMediaType(type);

        Resource file = tinymallStoreService.loadAsResource(key);
        if (file == null) {
            ResponseEntity.notFound();
        }
        return ResponseEntity.ok().contentType(mediaType).header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}
