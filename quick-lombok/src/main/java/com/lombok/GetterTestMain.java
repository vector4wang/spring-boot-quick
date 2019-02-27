package com.lombok;

import lombok.Getter;

/**
 * @Getter(lazy=true)：可以替代经典的Double Check Lock样板代码
 *
 * @author vector
 * @date: 2019/2/27 0027 11:00
 */
public class GetterTestMain {


    @Getter(lazy = true)
    private final double[] cached = expensive();

    private double[] expensive() {
        double[] result = new double[1000000];
        for (int i = 0; i < result.length; i++) {
            result[i] = Math.asin(i);
        }
        return result;
    }

    public static void main(String[] args) {

    }
}
