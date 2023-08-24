package com.quick.abs.strategy.base;

import org.springframework.beans.factory.InitializingBean;

/**
 * 生物
 */
public interface Creature extends InitializingBean {

    /**
     * 生物
     * @return
     */
    default boolean iThinkIam(){
        return true;
    }
}
