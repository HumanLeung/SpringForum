package com.company.springforum.service.register;


import com.company.springforum.pojo.Receiver;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class TempStorage {
    private static final Integer EXPIRE_MINUTES = 10;
    private final LoadingCache<String, Receiver> objectStore;
    public TempStorage() {
        objectStore = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MINUTES, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Receiver>() {
                    @Override
                    public Receiver load(String key) throws Exception {
                        return null;
                    }
                });
    }

    public void saveObject(Receiver receiver) {
        objectStore.put(receiver.getEmailAddress(),receiver);
    }
    public Receiver getObject(String name) throws ExecutionException {
        return objectStore.get(name);
    }
    public void clearObject(String name) {
        objectStore.invalidate(name);
    }
}
