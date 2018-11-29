package com.roedeer.test;

import oracle.jdbc.driver.OracleDriver;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by U6071369 on 11/15/2018.
 */
public class DbTest {

    @Test
    public void testConnection() {
        Connection conn = null;
        try {
            String DBurl = "jdbc:oracle:thin:@10.35.62.72:2521/IQMDEVAPP";
            String DBUser = "iqm_nav";
            String Password = "iqm_nav";
            DriverManager.deregisterDriver(new OracleDriver());
            conn = DriverManager.getConnection(DBurl, DBUser, Password);
            System.out.println(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
