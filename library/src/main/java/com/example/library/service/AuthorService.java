package com.example.library.service;

import com.example.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private RedisTemplate redisStringTemplate;

    public List<String> findAllAuthorName(){
        List<String> stringList = new ArrayList<>();

        String redisKey = "findAllAuthorName";

        boolean hasKey = redisStringTemplate.hasKey(redisKey);

        if(hasKey){
            stringList = redisStringTemplate.opsForList().range(redisKey, 0, -1);
        }
        else {
            stringList = authorRepository.findAllAuthorName();
            if(!stringList.isEmpty()){
                redisStringTemplate.opsForList().rightPushAll(redisKey, stringList);
            }
        }
        return stringList;
    }
}
