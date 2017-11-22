package com.xt.cloud.authservice;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xt.cloud.authservice.pojo.DirectoryInfo;
import com.xt.cloud.authservice.pojo.Node;
import com.xt.cloud.authservice.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by shawn on 2017/11/19.
 */
public class RedisManager {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * Login
     *
     * @param id            the id
     * @param clearPassword the clear password
     * @return access token
     */
    public String login(String id, String clearPassword){
        String password = (String) redisTemplate.opsForHash().get("users:"+id, "password");
        boolean match =  EncryptUtil.match(clearPassword, password);
        if (match){
            String token = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set("tokens:"+token, id, AuthConstants.TOKEN_TTL, TimeUnit.DAYS);
            return token;
        } else {
            return null;
        }
    }


    /**
     * Logout.
     *
     * @param token the token
     */
    public void logout(String token){
        redisTemplate.opsForValue().set("tokens:"+token, "0", 1, TimeUnit.MILLISECONDS);
    }


    /**
     * Check id
     *
     * @param id the id
     * @return the boolean
     */
    public boolean existId(String id){
        return redisTemplate.opsForHash().hasKey("users:"+id, "id");
    }



    /**
     * Verify token
     *
     * @param token the token
     * @return user id
     */
    public String verify(String token){
        String userId = redisTemplate.opsForValue().get("tokens:"+token);
        if (userId==null||"".equals(userId)){
            return "0";
        } else {
            return userId;
        }
    }



    /**
     * Add user
     *
     * @param user the user
     * @return always 1
     * @throws JsonProcessingException the json processing exception
     */
    public int addUser(User user) throws JsonProcessingException {
        //Generate root dir info
        DirectoryInfo dirInfo = new DirectoryInfo();
        Long newDirId = redisTemplate.opsForValue().increment("dircount",1);
        dirInfo.setId(String.valueOf(newDirId));
        dirInfo.setName(AuthConstants.DEFAULT_ROOT_DIR_NAME);
        dirInfo.setCreateTime(String.valueOf(System.currentTimeMillis()));
        //Save
        Map<String, String> dirMap = new HashMap<>();
        dirMap.put("id", dirInfo.getId());
        dirMap.put("name", dirInfo.getName());
        dirMap.put("createTime", dirInfo.getCreateTime());
        redisTemplate.opsForHash().putAll("dirs:"+newDirId, dirMap);

        //Generate root
        Node node = new Node();
        node.setDir(true);
        node.setValue(String.valueOf(newDirId));
        //Save
        redisTemplate.opsForValue().set("nodes:"+user.getId(), new ObjectMapper().writeValueAsString(node));

        //Save user
        Map<String, String> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("password", user.getPassword());
        userMap.put("createTime", user.getCreateTime());
        redisTemplate.opsForHash().putAll("users:"+user.getId(), userMap);

        return 1;
    }


}
