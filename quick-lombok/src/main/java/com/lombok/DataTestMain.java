package com.lombok;

import com.lombok.entity.Student;

/**
 * @author vector
 * @date: 2019/2/27 0027 10:30
 */
public class DataTestMain {
    public static void main(String[] args) {
        Student student = new Student();
        student.setClassNo("1");
        student.setStuNo("asdf");
        student.setAge(20);
        student.setAddress("sasfasdf");


        System.out.println(student);
    }
}
