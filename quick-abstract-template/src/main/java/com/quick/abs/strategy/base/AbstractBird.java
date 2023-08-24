package com.quick.abs.strategy.base;

import com.quick.abs.strategy.base.component.Oxygen;
import com.quick.abs.strategy.base.component.Water;
import com.quick.abs.strategy.base.component.Wing;
import lombok.Data;

import java.util.List;

@Data
public abstract class AbstractBird extends Animal {

    private Wing leftWing;
    private Wing rightWing;

    /**
     * 羽毛
     */
    private String feather;

    public AbstractBird() {
        this.leftWing = new Wing();
        this.rightWing = new Wing();
        this.feather = "红色的羽毛";
    }

    /**
     * 下蛋
     *
     * @return
     */
    public int layEggs() {
        System.out.println(this.getClass().getName() + " layEggs");
        return 1;
    }

    @Override
    public void metabolism(Oxygen oxygen, Water water) {
        System.out.println(this.getClass().getName() + " 需要氧气和水");
    }

    @Override
    public void reproduction() {
        System.out.println(this.getClass().getName() + " 可以繁殖");
    }
}
