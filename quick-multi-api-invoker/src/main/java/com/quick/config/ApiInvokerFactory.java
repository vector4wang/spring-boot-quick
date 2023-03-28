package com.quick.config;

import com.quick.enums.ApiInvokerType;
import com.quick.service.base.AbstractApiInvoker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
@Slf4j
public class ApiInvokerFactory implements InitializingBean, ApplicationContextAware {

    private static final Map<ApiInvokerType, AbstractApiInvoker> INVOKER_MAP = new EnumMap<>(ApiInvokerType.class);
    private ApplicationContext appContext;

    public AbstractApiInvoker getInvoker(ApiInvokerType invokerEnum) {
        return INVOKER_MAP.get(invokerEnum);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        appContext.getBeansOfType(AbstractApiInvoker.class)
                .values()
                .forEach(invoker -> INVOKER_MAP.put(invoker.getInvokerType(), invoker));

        log.info("init INVOKER_MAP: {}",INVOKER_MAP);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }
}