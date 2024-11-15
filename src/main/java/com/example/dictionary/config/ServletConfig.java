package com.example.dictionary.config;

import com.example.dictionary.servlet.AudioServlet;
import com.example.dictionary.servlet.MyServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<MyServlet> myServlet() {
        ServletRegistrationBean<MyServlet> servletRegistrationBean = new ServletRegistrationBean<>(new MyServlet(), "/MyServlet");
        servletRegistrationBean.setLoadOnStartup(1); // Сервлет будет загружен при старте
        return servletRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean<AudioServlet> audioServlet() {
        ServletRegistrationBean<AudioServlet> servletRegistrationBean = new ServletRegistrationBean<>(new AudioServlet(), "/audio");
        servletRegistrationBean.setLoadOnStartup(1); // Сервлет будет загружен при старте
        return servletRegistrationBean;
    }
}
