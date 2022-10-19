package com.rvbb.food.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.rvbb.food.template.service.adapter"})
public class TemplateApplication {

  public static void main(String[] args) {
    SpringApplication.run(TemplateApplication.class);
  }

}

