package com.menethil.tinymall.core.notify.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Configuration
@ConfigurationProperties(prefix = "tinymall.core.notify.WXNotifyConfig")
public class WXNotifyConfig {
    private boolean enable;
    private List<Map<String,String>> template = new ArrayList<>();

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<Map<String, String>> getTemplate() {
        return template;
    }

    public void setTemplate(List<Map<String, String>> template) {
        this.template = template;
    }
}
