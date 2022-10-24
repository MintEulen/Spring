package org.example.config;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
// 트랜잭션과 관련된 설정을 자동으로 해준다.
// 단, 사용자 간의 트랜잭션 처리를 위한 PlatformTransactionManager를 설정하기 위해서는
// TransactionManagement Configure를 구현하고,
// annotationDrivenTransactionManager 메소드를 오버라이딩 해야한다.

public class DBConfig implements TransactionManagementConfigurer {
    private String driverClassName = "com.mysql.jdbc.Driver";
    private String url= "jdbc:mysql://localhost:3306/connectdb?serverTimezone=Asia/Seoul&useSSL=false";
    private String username = "root";
    private String password = "1207";

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager();
    }
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
