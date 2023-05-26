package com.rvbb.food.template.config;

import com.rvbb.food.template.exception.AdapterBadRequestException;
import com.rvbb.food.template.service.adapter.CifClient;
import feign.Logger;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
@EnableFeignClients(clients = CifClient.class)
public class FeignConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            int status = response.status();
            if (status == 400) {
                String body = "400-Bad request";
                try {
                    body = IOUtils.toString(response.body().asReader(StandardCharsets.UTF_8));
                } catch (Exception ignored) {
                    log.debug("parser 400 bad request fail, cause by: ", ignored);
                }
                final HttpHeaders httpHeaders = new HttpHeaders();
                response.headers().forEach((k, v) -> httpHeaders.add(k + ".feign", StringUtils.join(v, ",")));
                return new AdapterBadRequestException(status, httpHeaders, body);
            } else {
                return new RuntimeException("Response Code " + status);
            }
        };
    }
}
