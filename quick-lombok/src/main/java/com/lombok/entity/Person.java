package com.lombok.entity;

import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author vector
 * @date: 2019/2/27 0027 10:10
 */
@ToString(exclude = "age")
@EqualsAndHashCode
@NoArgsConstructor // 无参构造
@RequiredArgsConstructor(staticName = "of") // @NonNull属性作为参数的构造函数
@AllArgsConstructor // 所有参数的构造函数
public class Person {
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    @NonNull
    private String name;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private int age;
    @Getter
    @Setter
    private Date birthday;
    @Getter
    @Setter
    private List<String> tmp;
}
