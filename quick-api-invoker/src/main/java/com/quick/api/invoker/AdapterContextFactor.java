package com.quick.api.invoker;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AdapterContextFactor {
    private static Map<String, Adapter> adapterMap = new HashMap<>();

    public static void register(String className, Adapter adapter) {
        log.info(" ===>>>> register adapter: {}", className);
        adapterMap.put(className, adapter);
    }


    public static <T> T getAdapter(String type, Class<T> clazz) {
        return (T) adapterMap.get(type);
    }
}
