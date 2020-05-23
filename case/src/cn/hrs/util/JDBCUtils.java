package cn.hrs.util;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *  JDBC工具类 使用Druid连接池
 */
public class JDBCUtils {
     private static DataSource ds;
     static {

         try {
             // 1.加载配置文件
             Properties prop = new Properties();
             // 使用类ClassLoader加载文件进内存，获取字符输入流
             InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
             prop.load(is);
             // 2.初始化连接池对象
             ds = DruidDataSourceFactory.createDataSource(prop);
         } catch (IOException e) {
             e.printStackTrace();
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

    /**
     * 获取连接池对象
     * @return
     */
     public static DataSource getDataSoure(){
         return ds;
     }
    /**
     * 获取连接Connection对象
     * @return
     */
     public static Connection getConnection() throws SQLException {
         return ds.getConnection();
     }
}
