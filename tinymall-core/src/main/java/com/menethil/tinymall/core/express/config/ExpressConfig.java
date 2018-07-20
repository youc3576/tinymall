package com.menethil.tinymall.core.express.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Configuration
@ConfigurationProperties(prefix = "tinymall.core.express")
public class ExpressConfig {
    private String appId;
    private String appKey;

    private List<Map<String,String>> vendors = new ArrayList<>();

    public List<Map<String,String>> getVendors() {
        return vendors;
    }

    public void setVendors(List<Map<String,String>> vendors) {
        this.vendors = vendors;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
