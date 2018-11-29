package com.roedeer.practice;

import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Roedeer on 11/26/2018.
 * Dmitri Ian
 */
public class NdaCopyImpl {
    public static void main(String[] args) {
        Connection conn = null;

        String DBurl = "jdbc:oracle:thin:@172.26.42.205:5703/CNR";
        String DBUser = "corecat";
        String Password = "corecat";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(DBurl, DBUser, Password);
            String sql = "select pro_name_d,value,ast_for_nda_id,begin_date,end_date from identifier where pro_nda_id = cdapi_ret_pkg.get_pro_fn('ISIN',   'ASSET',   'RBDM') and value = 'DE0005362263'";
            PreparedStatement ps;
            ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                System.out.println(resultSet.getString("ast_for_nda_id"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        String result = 0 + "    " + "";
        String number = result.substring(0,4).trim();
        long errCode = Long.valueOf(number);
        String errText = result.substring(5);
        if (errCode == 0) {
            System.out.println("world");
        }
        System.out.println(errCode + "    -" + errText + "----");
        System.out.println(Long.valueOf(null));
    }
}