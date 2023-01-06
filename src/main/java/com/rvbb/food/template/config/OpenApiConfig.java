package com.rvbb.food.template.config;

import java.awt.print.Pageable;

import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@EnableWebMvc
public class OpenApiConfig implements WebMvcConfigurer {

    static {
        SpringDocUtils.getConfig().replaceParameterObjectWithClass(Pageable.class, Pageable.class)
                      .replaceParameterObjectWithClass(PageRequest.class, Pageable.class);
    }

    @Bean
    public OpenAPI templateServiceOpenAPI(@Value("${app.descr}") String appDescription,
                                          @Value("${app.ver}") String appVersion) {
        return new OpenAPI()
                .info(new Info().title(appDescription)
                                .description(appDescription)
                                .version(appVersion)
                                .license(new License().name("RVBB Trademark").url("https://code.rvbb.com/license")))
                .externalDocs(new ExternalDocumentation()
                                      .description("Policy")
                                      .url("https://code.rvbb.com/policy"));
    }

//    @Bean
//    public GroupedOpenApi financeApi() {
//        return GroupedOpenApi.builder()
//                             .group("finance-service")
//                             .pathsToMatch("/finance/**")
//                             .build();
//    }
//
//    @Bean
//    public GroupedOpenApi testApi() {
//        return GroupedOpenApi.builder()
//                             .group("test-service")
//                             .pathsToMatch("/test/**")
//                             .build();
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
}
