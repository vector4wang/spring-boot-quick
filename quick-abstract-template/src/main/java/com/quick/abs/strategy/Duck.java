package com.quick.abs.strategy;

import com.quick.abs.factory.AbstractBirdFactory;
import com.quick.abs.strategy.base.AbstractBird;
import org.springframework.stereotype.Component;

@Component
public class Duck extends AbstractBird {
    @Override
    public void afterPropertiesSet() throws Exception {
        AbstractBirdFactory.register("Duck", this);
    }
}
