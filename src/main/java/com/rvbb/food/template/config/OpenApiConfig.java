package com.rvbb.food.template.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI templateServiceOpenAPI(@Value("${app.descr}") String appDescription, @Value("${app.ver}") String appVersion) {
        return new OpenAPI()
                .info(new Info().title(appDescription)
                                .description(appDescription)
                                .version(appVersion)
                                .license(new License().name("RVBB Trademark").url("https://code.rvbb.com/license")))
                .externalDocs(new ExternalDocumentation()
                                      .description("Policy")
                                      .url("https://code.rvbb.com/policy"));
    }

    @Bean
    public GroupedOpenApi financeApi() {
        return GroupedOpenApi.builder()
                             .group("finance")
                             .pathsToMatch("/**")
                             .build();
    }

}
