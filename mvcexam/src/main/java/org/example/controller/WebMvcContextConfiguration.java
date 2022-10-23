package org.example.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.example"})
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(31556926);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
        // 실제로 요청이 들어올 때 /js/ , /img/ /css/ 이렇게 시작하는 URL 요청은 각각 다른 어플리케이션 디렉터리를 만들어놓고 알맞게 사용하게 해준다.
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
        // configureDefaultServletHandling() 메소드를 재정의
        // configurer.enable()은 DefaultServletHttpRequestHandler(simple보다 우선순위 높음),
        // SimpleUrlHandlerMapping 두 bean 객체를 추가한다.
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("main");
        // 특정 URL에 대한 처리를 컨트롤러 클래스에 작성하지 않고 매핑할 수 있도록 해준다.
        // 요청 자체가 /라고 들어오면 main이라고 하는 이름의 뷰로 보여주도록 해준다.
    }

    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver(){ //뷰를 찾아주는 메소드
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");// 뷰 앞에 붙여라
        resolver.setSuffix(".jsp"); // 뷰 뒤에 붙여라 => WEB-INF/views/main/.jsp
        return resolver;
    }
}