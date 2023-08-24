package com.quick.abs.strategy;

import com.quick.abs.factory.AbstractBirdFactory;
import com.quick.abs.strategy.base.AbstractBird;
import org.springframework.stereotype.Component;

/**
 * 企鹅
 */
@Component
public class Penguin extends AbstractBird {


    @Override
    public void afterPropertiesSet() throws Exception {
        AbstractBirdFactory.register("Penguin", this);
    }
}
