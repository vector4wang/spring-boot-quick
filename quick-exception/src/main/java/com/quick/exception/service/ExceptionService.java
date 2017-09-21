package com.quick.exception.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/9/21
 * Time: 9:28
 * Description:
 */
@Service
public class ExceptionService {

    public double getResult(int fz,int fm) {
        List<String> list = new ArrayList<String>();
        list.add("beijing");
        list.add("shanghai");
        list.add("shanghai");
        list.add("guangzhou");
        list.add("shenzhen");
        list.add("hangzhou");
        String target = "hangzhou";
//        int size = list.size();
        for(String item : list){
            if(target.equals(item)){
                list.remove(item);
            }
        }
        System.out.println(list);
        return (double)fm/fz;
    }

    public static void main(String[] args) {

    }
}
