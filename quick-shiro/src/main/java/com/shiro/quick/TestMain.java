package com.shiro.quick;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestMain {



    public static void main(String[] args) {
        List<Integer> collect = Stream.of(1, 23, 45, 546, 456, 54, 7, 56, 45, 6, 43645).filter(item -> item > 1000).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }
}


