package com.quick.api.invoker;

import com.quick.api.invoker.model.AdapterClassModel;
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


    public static <T> T getAdapter(String name, Class<T> clazz) {
        return (T) adapterMap.get(name);
    }

    public static <T> T getAdapter(AdapterClassModel adapterClassModel) throws ClassNotFoundException {
        if (adapterClassModel.getLoadType() == 1) {
            return (T) getAdapter(adapterClassModel.getClassName(), Class.forName(adapterClassModel.getClassFqn()));
        } else {
            // TODO 实时加载代码源码
            return null;
        }
    }
}
