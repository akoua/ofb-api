package com.istic.ofbapi;

import com.istic.ofbapi.security.JwtAuthenticationFilter;
import com.istic.ofbapi.service.FileService;
import com.istic.ofbapi.service.impl.FileServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.convert.Jsr310Converters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackageClasses = {OfbApiApplication.class, Jsr310Converters.class})
public class OfbApiApplication {

    private final FileService fileService = new FileServiceImpl();

    public static void main(String[] args) {
        SpringApplication.run(OfbApiApplication.class, args);
    }


    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        fileService.init();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

}
