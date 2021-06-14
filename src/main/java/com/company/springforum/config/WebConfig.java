package com.company.springforum.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
                .allowedOrigins("http://192.168.43.19:3000/")
                .exposedHeaders("Authorization","check");

    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        String path = System.getProperty("user.dir")+"\\src\\main\\main\\resources\\static\\myforum\\" +
//                "*\\article\\bgi";
//    }
}
