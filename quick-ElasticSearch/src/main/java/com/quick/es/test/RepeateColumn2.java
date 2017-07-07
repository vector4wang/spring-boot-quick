package com.quick.es.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/6/26
 * Time: 17:49
 * Description:
 */
public class RepeateColumn2 {
    private static List<Student> stus = new ArrayList<Student>();

    public static void init() {

        for (int i = 0; i < 6; i++) {
            Student stu = new Student();
            if (3 > i) {
                stu.setName("小明");
            } else if (2 < i && 5 > i) {
                stu.setName("小华");
            } else {
                stu.setName("小小");
            }
            stu.setScore(20D);
            stus.add(stu);
        }
    }

    public static void main(String[] args) {

        init();

        Map<String, List<Object>> stuDataMap = new HashMap<String, List<Object>>();
        for (Student stu : stus) {
            String name = stu.getName();
            System.out.println("姓名：" + name);
            List<Object> dataList = stuDataMap.get(stu.getName());
            if (null == dataList) {
                dataList = new ArrayList<Object>();
                dataList.add(1);
                dataList.add(stu.getScore());
            } else {
                int count = (Integer) dataList.get(0) + 1;
                double score = (Double) dataList.get(1) + stu.getScore();
                dataList.remove(1);
                dataList.remove(0);
                dataList.add(count);
                dataList.add(score);
            }
            stuDataMap.put(stu.getName(), dataList);
        }

// 遍历结果
        for (Map.Entry<String, List<Object>> entry : stuDataMap.entrySet()) {
            System.out.println(entry.getKey() + "    " + entry.getValue().get(0) + "   " + entry.getValue().get(1));
        }
    }
}
class Student {

    private String name;

    private double score;

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

}
