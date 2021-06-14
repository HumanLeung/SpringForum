package com.company.springforum.service.email;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service("verifyCode")
public class VerifyCode {
    private static final Integer EXPIRE_MINUTES = 10;
    private final LoadingCache<String, Integer> otpCache;

    public VerifyCode() {
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MINUTES, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) throws Exception {
                        return 0;
                    }
                });
    }

    public int generateCode(String key) {
        Random random = new Random(System.currentTimeMillis());
        int code = random.nextInt(80000) + 10000;
        otpCache.put(key, code);
        return code;
    }

    public int getCode(String key) {
        try {
            return otpCache.get(key);
        } catch (Exception e) {
            return 0;
        }
    }

    public void clearOTP(String key) {
        otpCache.invalidate(key);
    }

}
