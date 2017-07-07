//package com.quick.es.test;
//
//import java.util.ArrayList;
//
///**
// * Created with IDEA
// * User: vector
// * Data: 2017/6/26
// * Time: 17:12
// * Description:
// */
//public class RepeateColumn {
//    public static void main(String[] args) {
//        ArrayList<Student> list = new ArrayList<Student>();
//        ArrayList<Student> newArrayList = new ArrayList<Student>();
//        //初始化测试数据
//        Student student = new Student("A", 50);
//        list.add(student);
//        student = new Student("A", 35);
//        list.add(student);
//        student = new Student("B", 100);
//        list.add(student);
//        student = new Student("C", 45);
//        list.add(student);
//        student = new Student("B", 45);
//        list.add(student);
//
//        int j;
//        Student studentI;//原始列表中的学生
//        Student studentJ;//整理后的列表中的学生
//        for (int i = 0; i < list.size(); i++) {
//            studentI = list.get(i);
//            for (j = 0; j < newArrayList.size(); j++) {
//                studentJ = newArrayList.get(j);
//                if (studentI.getName().equals(studentJ.getName())) {//原始列表中的学生已经在新列表中出现过
//                    studentJ.setCount(studentJ.getCount() + 1);//出现次数加一
//                    studentJ.setTotal(studentJ.getTotal() + studentI.getScore());//总成绩加上这次的成绩
//                    break;//跳出J循环
//                }
//            }
//            if (j == newArrayList.size()) {//原始列表中的这个学生没在整理后的列表中出现过
//                studentI.setCount(1);
//                studentI.setTotal(studentI.getScore());
//                newArrayList.add(studentI);
//            }
//
//        }
//        //用于测试结果
//        Student studentK;
//        for (int k = 0; k < newArrayList.size(); k++) {
//            studentK = newArrayList.get(k);
//            System.out.println(studentK.getName() + "出现" + studentK.getCount()
//                    + "次， 总成绩：" + studentK.getTotal());
//        }
//    }
//}
//class Student {
//    private String name;
//    private int score;
//    private int count;
//    private int total;
//
//    public Student(String name, int score) {
//        this.name = name;
//        this.score = score;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getScore() {
//        return score;
//    }
//
//    public void setScore(int score) {
//        this.score = score;
//    }
//
//    public int getCount() {
//        return count;
//    }
//
//    public void setCount(int count) {
//        this.count = count;
//    }
//
//    public int getTotal() {
//        return total;
//    }
//
//    public void setTotal(int total) {
//        this.total = total;
//    }
//}