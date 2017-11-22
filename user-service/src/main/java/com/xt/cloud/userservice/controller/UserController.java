package com.xt.cloud.userservice.controller;

import com.xt.cloud.userservice.EncryptUtil;
import com.xt.cloud.userservice.RedisManager;
import com.xt.cloud.userservice.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.util.EncodingUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Random;

/**
 * Created by shawn on 2017/11/18.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private RedisManager redisManager;

    @PostMapping(value = "/")
    public String add(HttpServletRequest request){
        String name = request.getParameter("name");

        String clearPassword = request.getParameter("password");
        String password = EncryptUtil.encrypt(clearPassword);
        String createTime = String.valueOf(new Date().getTime());
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setCreateTime(createTime);
        redisManager.addUser(user);
        return "ok";
    }

    @GetMapping(value = "/")
    public HttpServletResponse test(HttpServletResponse response){
        Assert.hasText("", "昵称不能为空");
        return response;
    }


}
