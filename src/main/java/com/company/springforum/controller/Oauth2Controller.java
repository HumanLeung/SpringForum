package com.company.springforum.controller;


import com.company.springforum.model.User;
import com.company.springforum.service.oauth2.Oauth2Service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@CrossOrigin(origins = "*",  allowedHeaders = "*")
@RestController
@RequestMapping("/api/oauth2")
public class Oauth2Controller {

    private final RedisTemplate<String, String> redisTemplate;
    private final Oauth2Service oauth2Service;


    public Oauth2Controller(RedisTemplate<String,String> redisTemplate, Oauth2Service oauth2Service) {
        this.redisTemplate = redisTemplate;
        this.oauth2Service = oauth2Service;
    }

    @GetMapping("/info/{key}")
    public void loginByOauth(@PathVariable String key) {
        System.out.println(key + "this is key");
        redisTemplate.opsForValue().set("oauthKey",key);
    }
    @GetMapping("/success")
    public void success(HttpServletResponse response) throws IOException {
        System.out.println("success");
    }

    @GetMapping("/getInfo/{userNum}")
    public User getInfo(@PathVariable String userNum) {
        System.out.println(userNum + " this after sign in");
        String userNums = redisTemplate.opsForValue().get(userNum);
        return oauth2Service.getUser(userNums);
    }

    @GetMapping("/getUser/{num}")
    public User getByNum(@PathVariable String num) {
        return oauth2Service.getUser(num);
    }
}
