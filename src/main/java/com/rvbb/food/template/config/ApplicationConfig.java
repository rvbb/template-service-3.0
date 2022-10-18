package com.rvbb.food.template.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ApplicationConfig {
    @Value("${paging.page:0}")
    private int page;
    @Value("${paging.size:50}")
    private int size;
    @Value("${paging.size.allowed_max:500}")
    private int allowedMaxSize;
}
