package com.menethil.tinymall.core.store;

import com.menethil.tinymall.core.util.CharUtil;
import com.menethil.tinymall.db.domain.TinymallStorage;
import com.menethil.tinymall.db.service.TinymallStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TinymallStoreService {
    @Resource(name = "${tinymall.core.store.activeStorage}")
    private ObjectStorageService storageService;
    @Autowired
    private TinymallStorageService tinymallStorageService;

    private String generateKey(String originalFilename) {
        int index = originalFilename.lastIndexOf('.');
        String suffix = originalFilename.substring(index);

        String key;
        TinymallStorage storageInfo;

        do {
            key = CharUtil.getRandomString(20) + suffix;
            storageInfo = tinymallStorageService.findByKey(key);
        }
        while (storageInfo != null);

        return key;
    }

    public TinymallStorage create(MultipartFile file,String localUrl) {
        String originalFilename = file.getOriginalFilename();

        String key = generateKey(originalFilename);
        storageService.store(file, key);

        String url = storageService.generateUrl(key,localUrl);
        TinymallStorage storageInfo = new TinymallStorage();
        storageInfo.setName(originalFilename);
        storageInfo.setSize((int) file.getSize());
        storageInfo.setType(file.getContentType());
        storageInfo.setAddTime(LocalDateTime.now());
        storageInfo.setModified(LocalDateTime.now());
        storageInfo.setKey(key);
        storageInfo.setUrl(url);
        tinymallStorageService.add(storageInfo);
        return storageInfo;
    }

    public TinymallStorage read(Integer id) {
        if (id == null) {
            return null;
        }
        return tinymallStorageService.findById(id);
    }

    public void update(TinymallStorage tinymallStorage) {
        tinymallStorageService.update(tinymallStorage);
    }

    public void delete(TinymallStorage tinymallStorage) {
        tinymallStorageService.deleteByKey(tinymallStorage.getKey());
        storageService.delete(tinymallStorage.getKey());
    }

    public org.springframework.core.io.Resource loadAsResource(String key) {
        return storageService.loadAsResource(key);
    }

    public TinymallStorage findByKey(String key) {
        return tinymallStorageService.findByKey(key);
    }

    public int countSelective(String key, String name, Integer page, Integer limit, String sort, String order) {
        return tinymallStorageService.countSelective(key, name, page, limit, sort, order);
    }

    public List<TinymallStorage> querySelective(String key, String name, Integer page, Integer limit, String sort, String order) {
        return tinymallStorageService.querySelective(key, name, page, limit, sort, order);
    }
}
