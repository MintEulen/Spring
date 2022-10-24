package org.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@ComponentScan(basePackages = {"org.example.dao","org.example.service"})
@Import({DBConfig.class}) // 위 부분들이 수행될 때 내부적으로 DBConfig에 사용되고 있는 것들을 사용해야 해서 해당 부분을 Import
public class ApplicationConfig {
}