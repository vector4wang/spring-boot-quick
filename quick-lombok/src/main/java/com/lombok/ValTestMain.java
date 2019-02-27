package com.lombok;

import lombok.val;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * val:用在局部变量前面，相当于将变量声明为final
 * @author vector
 * @date: 2019/2/27 0027 9:52
 */
public class ValTestMain {
    public static void main(String[] args) {
        val sets = new HashSet<>();// ===> final Set sets = new HashSet<>();
        val lists = new ArrayList<>();// ===> final List<String> lists = new ArrayList<>();
        val str = "123";// ===> final String str = "123";

        System.out.println(str);

    }
}
