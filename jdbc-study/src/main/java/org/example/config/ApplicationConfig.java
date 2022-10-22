package org.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"org.example.dao"})
@Import({DBConfig.class})  // 설정파일을 여러개로 나워서 작성할 수 있게 해주는 어노테이션
// 이 설정에다가 모든 설정을 다 넣는게 아니라 다른 설정(DB연결)은 따로 빼주고 싶을 때 사용한다.
public class ApplicationConfig {
}