package com.menethil.tinymall.wx.service;

import com.menethil.tinymall.db.domain.TinymallUser;
import com.menethil.tinymall.db.service.TinymallUserService;
import com.menethil.tinymall.wx.dao.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserInfoService {
    @Autowired
    private TinymallUserService userService;


    public UserInfo getInfo(Integer userId) {
        TinymallUser user = userService.findById(userId);
        Assert.state(user != null, "用户不存在");
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(user.getNickname());
        userInfo.setAvatarUrl(user.getAvatar());
        return userInfo;
    }


}
