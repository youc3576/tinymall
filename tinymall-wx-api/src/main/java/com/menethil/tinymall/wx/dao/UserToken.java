package com.menethil.tinymall.wx.dao;

import java.time.LocalDateTime;

/**
 * 用户访问 Token 信息
 */
public class UserToken {
    private Integer userId;
    private String token;
    private LocalDateTime expireTime;
    private LocalDateTime updateTime;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
}
