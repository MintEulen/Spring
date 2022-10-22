package org.example.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;



    @Configuration
    @EnableTransactionManagement // 트랜젝션 매니저가 자동으로 활성화
    public class DBConfig {
        private String driverClassName = "com.mysql.jdbc.Driver";
        private String url = "jdbc:mysql://localhost:3306/connectdb?serverTimezone=Asia/Seoul&useSSL=false";

        private String username = "root";
        private String password = "1207";

        @Bean
        public DataSource dataSource() { // 메소드의 이름은 Bean등록 시 id로 지정한 이름과 같다.
            // DataSource가 커넥션을 관리하기 위해 필요한 JDBC 드라이버 url, username, password 등의 정보를 알려준다.
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            return dataSource;

        }
}
