package com.quick.abs.strategy.base;

import com.quick.abs.strategy.base.component.Oxygen;
import com.quick.abs.strategy.base.component.Water;

import java.util.List;

public abstract class Animal implements Creature {

    /**
     * 是否活着
     *
     * @return 默认活着
     */
    public boolean isAlive() {
        return true;
    }


    /**
     * 新陈代谢
     */
    public abstract void metabolism(Oxygen oxygen, Water water);

    /**
     * 繁殖
     *
     * @return 小动物
     */
    public abstract void reproduction();


}
