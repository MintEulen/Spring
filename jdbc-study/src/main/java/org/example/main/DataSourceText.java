package org.example.main;

import org.example.config.ApplicationConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;

public class DataSourceText {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        DataSource ds = ac.getBean(DataSource.class); // dataSource를 구현하고 있는 객체를 나한테 리턴
        Connection conn = null;
        try {
            conn = ds.getConnection(); //getConnection() 메소드를 이용해 connection을 얻어온다.
            if (conn != null) // connection을 얻어왔는데 null이 아니라면 접속이 잘 된 거라 접속 성공을 해주면된다.
                System.out.println("접속 성공");
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
