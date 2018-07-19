package com.menethil.tinymall.wx.service;

import com.menethil.tinymall.core.util.CharUtil;
import com.menethil.tinymall.wx.dao.UserToken;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户访问 Token信息 管理器，缓存了用户访问的 TOKEN信息
 */
public class UserTokenManager {
    private static Map<String, UserToken> tokenMap = new HashMap<>();
    private static Map<Integer, UserToken> idMap = new HashMap<>();

    /**
     * 从缓存的 TOKEN 中查询返回用户ID
     *
     * @param token
     * @return
     */
    public static Integer getUserId(String token) {


        UserToken userToken = tokenMap.get(token);
        if (userToken == null) {
            return null;
        }

        if (userToken.getExpireTime().isBefore(LocalDateTime.now())) {
            tokenMap.remove(token);
            idMap.remove(userToken.getUserId());
            return null;
        }

        return userToken.getUserId();
    }


    /**
     * 生成用户访问的 TOKEN
     *
     * @param id
     * @return
     */
    public static UserToken generateToken(Integer id) {
        UserToken userToken = null;

        String token = CharUtil.getRandomString(32);
        while (tokenMap.containsKey(token)) {
            token = CharUtil.getRandomString(32);
        }

        LocalDateTime update = LocalDateTime.now();
        LocalDateTime expire = update.plusDays(1);

        userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUpdateTime(update);
        userToken.setExpireTime(expire);
        userToken.setUserId(id);
        tokenMap.put(token, userToken);
        idMap.put(id, userToken);

        return userToken;
    }
}
