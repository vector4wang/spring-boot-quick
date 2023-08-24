package com.quick.abs.service;

import com.quick.abs.factory.AbstractBirdFactory;
import com.quick.abs.strategy.base.AbstractBird;
import com.quick.abs.strategy.base.component.Oxygen;
import com.quick.abs.strategy.base.component.Water;
import org.springframework.stereotype.Service;

@Service
public class CreatureLifeService {


    public void birth2die(String key) {
        AbstractBird bird = AbstractBirdFactory.getInvokeHandler(key);

        // 活着
        bird.isAlive();

        // 新晨代谢
        bird.metabolism(new Oxygen(), new Water());

        System.out.println(bird.getFeather());


        // 飞翔
        bird.getLeftWing().action();
        bird.getLeftWing().action();

        // 可以繁殖
        bird.reproduction();

        bird.layEggs();


    }

}
