package com.roedeer.io;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 8/1/2019 5:06 PM
 **/
public class ReadExcel {
    public static void main(String[] args) {
        ExcelReader reader = ExcelUtil.getReader(ResourceUtil.getStream("RIC.xlsx"));
        List<List<Object>> read = reader.read();
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 1; i < read.size() - 1; i++) {
//            System.out.println(read.get(i).get(0));
            sb.append("\'" + read.get(i).get(0) + "\'" + ",");
        }
        sb.append("\'" + read.get(read.size() - 1).get(0) + "\'");
        sb.append(")");
        System.out.println(sb);
    }
}
