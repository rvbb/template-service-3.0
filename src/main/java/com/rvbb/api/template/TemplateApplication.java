package com.rvbb.api.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.rvbb.api.template.service.adapter"})
public class TemplateApplication {

  public static void main(String[] args) {
    SpringApplication.run(TemplateApplication.class);
  }

}
