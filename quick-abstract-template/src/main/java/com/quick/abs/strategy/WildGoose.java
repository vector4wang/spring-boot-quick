package com.quick.abs.strategy;

import com.quick.abs.factory.AbstractBirdFactory;
import com.quick.abs.strategy.base.AbstractBird;
import com.quick.abs.strategy.base.ability.Fly;
import org.springframework.stereotype.Component;

/**
 * 大雁
 */
@Component
public class WildGoose extends AbstractBird implements Fly {


    @Override
    public void afterPropertiesSet() throws Exception {
        AbstractBirdFactory.register("WildGoose", this);
    }


    public void flyByV(){
        action();
        System.out.println("use V fly");
    }

    public void flyByLine(){
        action();
        System.out.println("use Line fly");
    }


    @Override
    public void action() {
        System.out.println(this.getClass().getName()+" can fly");
    }
}
