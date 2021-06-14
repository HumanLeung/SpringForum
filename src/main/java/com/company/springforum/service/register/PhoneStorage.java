package com.company.springforum.service.register;


import com.company.springforum.pojo.Phone;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


@Service
public class PhoneStorage {
    private static final Integer EXPIRE_MINUTES = 10;
    private final LoadingCache<String, Phone> objectStore;
    public PhoneStorage() {
        objectStore = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MINUTES, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Phone>() {
                    @Override
                    public Phone load(String key) throws Exception {
                        return null;
                    }
                });
    }

    public void saveObject(Phone phone) {
        objectStore.put(phone.getPhone(),phone);
    }
    public Phone getObject(String phone) throws ExecutionException {
        return objectStore.get(phone);
    }
    public void clearObject(String phone) {
        objectStore.invalidate(phone);
    }
}
