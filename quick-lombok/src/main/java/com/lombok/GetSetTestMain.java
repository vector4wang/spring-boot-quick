package com.lombok;

import com.lombok.entity.Person;

import java.util.ArrayList;
import java.util.Date;

/**
 * @Getter/@Setter：用在属性上，再也不用自己手写setter和getter方法了，还可以指定访问范围
 * @ToString：用在类上，可以自动覆写toString方法，当然还可以加其他参数，例如@ToString(exclude=”id”)排除id属性，或者@ToString(callSuper=true, includeFieldNames=true)调用父类的toString方法，包含所有属性
 * @EqualsAndHashCode：用在类上，自动生成equals方法和hashCode方法
 * @NoArgsConstructor, @RequiredArgsConstructor and @AllArgsConstructor：用在类上，自动生成无参构造和使用所有参数的构造函数以及把所有@NonNull属性作为参数的构造函数，如果指定staticName = “of”参数，同时还会生成一个返回类对象的静态工厂方法，比使用构造函数方便很多
 *
 * @Data：注解在类上，相当于同时使用了@ToString、@EqualsAndHashCode、@Getter、@Setter和@RequiredArgsConstrutor这些注解，对于POJO类十分有用
 * @author vector
 * @date: 2019/2/27 0027 10:05
 */
public class GetSetTestMain {
    public static void main(String[] args) {
        Person persion = new Person();


        persion.setAddress("hahaha");
        //persion.setName("test");// not access
        persion.setAge(12);
        persion.setBirthday(new Date());


        System.out.println(persion.toString());


        Person persion1 = new Person("hahah","深圳",11,new Date(),new ArrayList<>());

        Person wxc = Person.of("wxc");
        System.out.println(wxc);


    }
}
