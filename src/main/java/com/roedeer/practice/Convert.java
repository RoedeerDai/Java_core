package com.roedeer.practice;

import org.junit.Test;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

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

    @Test
    public void testJdk8() {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("Avro");
        list.add("rpc");
        list.add("java");
        list.forEach(o -> {System.out.println(o);});
    }

    @Test
    public void testObject() {
        String[] args = {"a", "b"};
        Object obj = args;
        ((String []) obj)[0] = "object";
        System.out.printf(args[0]);
        Arrays.asList(new ArrayList<>());

        SimpleDateFormat sdfHourlyFormat = new SimpleDateFormat("yyyy-MM-dd-HHmm");
        String hourlyDate=sdfHourlyFormat.format(new Date());
        System.out.println(hourlyDate);

    }

    @Test
    public void testO() {
        String str = "aaabbb";
        System.out.println(str.substring(0,str.length()-1));
    }





}
