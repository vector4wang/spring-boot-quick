package com.quick.es.util;

import com.quick.es.entity.City;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

public class JsonUtil {

    // Java实体对象转json对象
    public static String model2Json(City city) {
        String jsonData = null;
        try {
            XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
            jsonBuild.startObject()
                    .field("id", city.getId())
                    .field("name", city.getName())
                    .field("countrycode", city.getCountrycode())
                    .field("district", city.getDistrict())
                    .field("population", city.getPopulation())
                    .endObject();

            jsonData = jsonBuild.string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

}