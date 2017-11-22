package com.xt.cloud.userservice;

import com.xt.cloud.userservice.pojo.Directory;
import com.xt.cloud.userservice.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shawn on 2017/11/19.
 */
public class RedisManager {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public int addUser(User user){
        Long newUserId = redisTemplate.opsForValue().increment("usercount",1);
        user.setId(String.valueOf(newUserId));

        Directory dir = new Directory();
        Long newDirId = redisTemplate.opsForValue().increment("dircount",1);
        dir.setId(String.valueOf(newDirId));
        dir.setName("根目录");
        dir.setCreateTime(String.valueOf(new Date().getTime()));

        Map<String, String> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("name", user.getName());
        userMap.put("password", user.getPassword());
        userMap.put("createTime", user.getCreateTime());
        redisTemplate.opsForHash().putAll("users:"+newUserId, userMap);

        Map<String, String> dirMap = new HashMap<>();
        dirMap.put("id", dir.getId());
        dirMap.put("name", dir.getName());
        dirMap.put("createTime", dir.getCreateTime());
        redisTemplate.opsForHash().putAll("dirs:"+newDirId, dirMap);
        return 1;
    }


}
