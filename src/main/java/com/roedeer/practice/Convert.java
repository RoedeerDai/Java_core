package com.roedeer.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by U6071369 on 11/22/2018.
 */
public class Convert {
    public static void main(String[] args) {
        NdaCopyProperty property = new NdaCopyProperty();
        property.setPro_name("permid");
        property.setPro_value("DE2315689");
        List<NdaCopyProperty> list = new ArrayList<NdaCopyProperty>();
        list.add(property);
        NdaCopyProperty property1 = new NdaCopyProperty();
        property1.setPro_name("pilc");
        property1.setPro_value("sdfeadsfe");
        list.add(property1);
        NdaCopyProperty property2 = new NdaCopyProperty();
        property2.setPro_name("rcs_value");
        property2.setPro_value("ORD");
        list.add(property2);
        NdaCopyProperty property3 = new NdaCopyProperty();
        property3.setPro_name("end_date");
        property3.setPro_value("20010205");
        list.add(property3);


        HashMap<String, NdaCopyProperty> map = new HashMap<String, NdaCopyProperty>();

        for (int i = 0; i < list.size(); i++) {
            String key = list.get(i).getPro_name();
            map.put(key, list.get(i));
        }
        System.out.println(map.get("rcs_value").getPro_value());
    }
}
