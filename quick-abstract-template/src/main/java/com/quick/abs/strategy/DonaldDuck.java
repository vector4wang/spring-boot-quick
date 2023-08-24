package com.quick.abs.strategy;

import com.quick.abs.factory.AbstractBirdFactory;
import com.quick.abs.strategy.base.ability.Fly;
import com.quick.abs.strategy.base.ability.Speak;
import org.springframework.stereotype.Component;

@Component
public class DonaldDuck extends Duck implements Speak, Fly {



    @Override
    public void afterPropertiesSet() throws Exception {
        AbstractBirdFactory.register("DonaldDuck", this);
    }


    @Override
    public void action() {
        System.out.println(this.getClass().getName()+" can speak");
    }
}
