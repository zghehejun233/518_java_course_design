package org.fatmansoft.teach.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 16645
 */
@Repository
public class GlobalScoreRepository {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void leftPushStringList(String key, List<String> value) {
        stringRedisTemplate.opsForList().leftPushAll(key, value);
    }

    public List<String> getRangeFrom02Index(String key, long index) {
        return redisTemplate.opsForList().range(key, 0, index);
    }

    public List<String> getAllRange(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public void drop(String key) {
        stringRedisTemplate.delete(key);
    }



}
