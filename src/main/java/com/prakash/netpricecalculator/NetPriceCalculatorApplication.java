package com.prakash.netpricecalculator;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "An Application for Net Price Calculator", version = "1.0.0"))
public class NetPriceCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetPriceCalculatorApplication.class, args);
    }

}
