package com.xt.cloud.authservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xt.cloud.authservice.EncryptUtil;
import com.xt.cloud.authservice.RedisManager;
import com.xt.cloud.authservice.pojo.Response;
import com.xt.cloud.authservice.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by shawn on 2017/11/20.
 */
@RestController
public class AuthController {
    @Autowired
    private RedisManager redisManager;


    @PostMapping("/register")
    public Response register(HttpServletRequest request) throws JsonProcessingException {
        String id = request.getParameter("id");
        Assert.hasText(id, "用户名不能为空~");
        String clearPassword = request.getParameter("password");
        Assert.hasText(clearPassword, "密码不能为空~");

        if (redisManager.existId(id)){
            return new Response(2, "用户名已存在~");
        }

        String password = EncryptUtil.encrypt(clearPassword);
        String createTime = String.valueOf(System.currentTimeMillis());
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        user.setCreateTime(createTime);
        redisManager.addUser(user);
        return new Response("注册成功~");
    }

    @PostMapping("/login")
    public Response login(HttpServletRequest request){
        String id = request.getParameter("id");
        Assert.hasText(id, "用户名不能为空~");
        String clearPassword = request.getParameter("password");
        Assert.hasText(clearPassword, "密码不能为空~");

        if (!redisManager.existId(id)){
            return new Response(0, "用户不存在~");
        }

        String token =  redisManager.login(id, clearPassword);
        if (token==null){
            return new Response(2, "密码不正确~");
        } else {
            return new Response(1, "登录成功~", token);
        }

    }


    @PostMapping("/verify")
    public Response verify(String token){
        String result = redisManager.verify(token);
        if ("0".equals(result)){
            return new Response(2, "验证失败~");
        } else {
            return new Response(1, "验证成功~", result);
        }
    }

    @PostMapping("/logout")
    public Response logout(String token){
        redisManager.logout(token);
        return new Response("已登出~");
    }



}
