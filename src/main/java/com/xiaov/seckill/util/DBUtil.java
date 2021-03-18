package com.xiaov.seckill.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @author xiaov
 * @since 2021-03-08 20:49
 */
public class DBUtil {

    private static Properties properties;

    static {
        InputStream in = null;
        try {
            in = DBUtil.class.getClassLoader().getResourceAsStream("application.properties");
            properties = new Properties();
            properties.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Connection getConn() throws Exception {
        String url = properties.getProperty("spring.datasource.url");
        String username = properties.getProperty("spring.datasource.username");
        String password = properties.getProperty("spring.datasource.password");
//        String driver = properties.getProperty("spring.datasource.driver-class-name");
//        Class.forName(driver);
        return DriverManager.getConnection(url,username,password);
    }
}
