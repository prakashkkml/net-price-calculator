package com.prakash.netpricecalculator.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public KeyGenerator countryVatKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName());
                sb.append(method.getName());
                for (Object param : params) {
                    if (!ObjectUtils.isEmpty(param))
                        sb.append(param);
                }
                return sb.toString();
            }
        };
    }
}
