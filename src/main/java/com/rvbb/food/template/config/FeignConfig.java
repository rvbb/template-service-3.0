package com.rvbb.food.template.config;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rvbb.food.template.exception.AdapterBadRequestException;

import feign.Logger;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

@Slf4j
@Configuration
@EnableFeignClients
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
                    log.debug("parser 400 bad request fail, cause by:[{}]", ignored);
                }
                final HttpHeaders httpHeaders = new HttpHeaders();
                response.headers().forEach((k, v) -> httpHeaders.add(k + ".feign", StringUtils.join(v, ",")));
                return new AdapterBadRequestException(status, httpHeaders, body);
            } else {
                return new RuntimeException("Response Code " + status);
            }
        };
    }

//    @Bean
    public Decoder decoder() {
        HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(makeConverter());
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }

    private ObjectMapper makeConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper;
    }

    public Encoder encoder() {
        return null;
    }
}
