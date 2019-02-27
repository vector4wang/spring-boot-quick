package com.lombok;

import com.lombok.entity.Adult;

/**
 * @Builder：用在类、构造器、方法上，为你提供复杂的builder APIs，让你可以像如下方式一样调用Person.builder().name("Adam Savage").city("San Francisco").job("Mythbusters").job("Unchained Reaction").build();
 * @Singular
 * @author vector
 * @date: 2019/2/27 0027 10:50
 */
public class BuilderTestMain {
    public static void main(String[] args) {
        Adult build = Adult.builder().age(10).cd("q").cd("e").cd("w").name("zxc").build();
        System.out.println(build);

    }
}
