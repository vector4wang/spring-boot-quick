package com.quick.api.invoker;

import org.springframework.beans.factory.InitializingBean;

public interface Adapter extends InitializingBean {
    default String getType() {
        return this.getClass().getSimpleName();
    }

    @Override
    default void afterPropertiesSet() throws Exception {
        AdapterContextFactor.register(getType(), this);
    }
}
