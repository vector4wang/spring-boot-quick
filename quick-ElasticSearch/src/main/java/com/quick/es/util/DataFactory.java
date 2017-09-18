package com.quick.es.util;

import com.quick.es.entity.City;

import java.util.ArrayList;
import java.util.List;

public class DataFactory {
    public static DataFactory dataFactory = new DataFactory();

    private DataFactory() {
    }

    public DataFactory getInstance() {
        return dataFactory;
    }

    public static List<String> getInitJsonData() {
        List<String> list = new ArrayList<String>();
        String data1 = JsonUtil.model2Json(new City(6, "Kabul", "AFG", "Herat",1780000));
//        String data2 = JsonUtil.model2Json(new City(2, "Qandahar", "AFG", "Qandahar",237500));
//        String data3 = JsonUtil.model2Json(new City(3, "Herat", "AFG", "Herat",186800));
//        String data4 = JsonUtil.model2Json(new City(4, "Mazar-e-Sharif", "AFG", "Balkh",127800));
//        String data5 = JsonUtil.model2Json(new City(5, "Amsterdam", "AFG", "Noord-Holland",731200));
        list.add(data1);
//        list.add(data2);
//        list.add(data3);
//        list.add(data4);
//        list.add(data5);
        return list;
    }

}