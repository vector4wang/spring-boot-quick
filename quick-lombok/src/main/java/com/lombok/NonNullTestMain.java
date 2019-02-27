package com.lombok;

import lombok.NonNull;

/**
 * `@NonNull` 给方法参数增加这个注解会自动在方法内对该参数进行是否为空的校验，如果为空，则抛出NPE（NullPointerException）
 *
 * @author vector
 * @date: 2019/2/27 0027 9:59
 */
public class NonNullTestMain {
    public static void main(String[] args) {
//        notNullLB(null);
        notNull(null);
    }

    /**
     * Exception in thread "main" java.lang.NullPointerException: arg
     * @param arg
     */
    public static void notNullLB(@NonNull String arg) {
        System.out.println(arg.length());
    }

    /**
     * Exception in thread "main" java.lang.NullPointerException
     * @param arg
     */
    public static void notNull(String arg) {
        System.out.println(arg.length());
    }
}
