package com.quick.abs.factory;

import com.quick.abs.strategy.base.AbstractBird;
import com.quick.abs.strategy.base.Animal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 生物工场
 */
@Slf4j
public class AbstractBirdFactory {
    private static Map<String, AbstractBird> map = new HashMap<>();

    public static void register(String key, AbstractBird bird) {
        if (StringUtils.isEmpty(key) && null == bird) {
            return;
        }
        map.put(key, bird);
    }

    public static AbstractBird getInvokeHandler(String key) {
        log.info("register {}", key);
        return map.get(key);
    }

}
