package com.company.springforum.service.oauth2;


import com.company.springforum.dto.PhoneUserDto;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


@Component
public class OauthStorage {

    private static final Integer EXPIRE_MINUTES = 10;
    private final LoadingCache<String, PhoneUserDto> objectStore;
    public OauthStorage() {
        objectStore = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MINUTES, TimeUnit.MINUTES)
                .build(new CacheLoader<String, PhoneUserDto>() {
                    @Override
                    public PhoneUserDto load(String key) throws Exception {
                        return null;
                    }
                });
    }

    public void saveObject(String digit, PhoneUserDto phoneUserDto) {
        objectStore.put(digit,phoneUserDto);
    }
    public PhoneUserDto phoneUserDto(String digit) throws ExecutionException {
       return objectStore.get(digit);
    }
    public void clearObject(String digit)
    {
        objectStore.invalidate(digit);
    }
}
