//package ru.otus;
//
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@EnableWebMvc
//@Configuration
//@ComponentScan(basePackages = {"ru.otus.controllers"})
//public class ServletConfig implements WebMvcConfigurer {
//
//    // Configuration to render VIEWS
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        registry.jsp("/WEB-INF/views/", ".jsp");
//    }
//}