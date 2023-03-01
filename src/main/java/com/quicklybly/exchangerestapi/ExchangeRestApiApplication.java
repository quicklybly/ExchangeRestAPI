package com.quicklybly.exchangerestapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@OpenAPIDefinition(info = @Info(title = "ExchangeRestApi",
        description = "This API allows to emulate crypto exchange work. " +
                "There are two types of users: Regular user and Admin" +
                "Regular users can manipulate theirs wallets (deposit, withdraw, exchange, etc.) " +
                "Admin can change exchange rate and collect statistics"))
public class ExchangeRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeRestApiApplication.class, args);
    }

}
