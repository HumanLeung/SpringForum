package com.company.springforum.service.sms;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class CodeGenerator {
    private static final Integer EXPIRE_MINUTE = 6;
    private final LoadingCache<String, Integer> digitCode;
    public CodeGenerator() {
    super();
    digitCode = CacheBuilder
            .newBuilder()
            .expireAfterWrite(EXPIRE_MINUTE, TimeUnit.MINUTES)
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
        digitCode.put(key, code);
        return code;
    }
    public int getCode(String key){
        try{
            return digitCode.get(key);
        }catch (Exception e){
            return 0;
        }
    }
    public void clearCode(String key){
        digitCode.invalidate(key);
    }
}
