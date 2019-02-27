package com.lombok;

import com.lombok.entity.Teacher;

/**
 * @Value：用在类上，是@Data的不可变形式，相当于为属性添加final声明，只提供getter方法，而不提供setter方法
 * @author vector
 * @date: 2019/2/27 0027 10:40
 */
public class ValueTestMain {
    public static void main(String[] args) {
        Teacher teacher = new Teacher("wxc");
        // teacher.setSalary() 没有setter方法
    }
}
