package com.rvbb.food.template.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import lombok.Getter;

@Configuration
@Getter
@EnableCaching
@EnableWebMvc
public class ApplicationConfig {
    @Value("${paging.page:0}")
    private int page;
    @Value("${paging.size:50}")
    private int size;
    @Value("${paging.max-size:500}")
    private int allowedMaxSize;
}
