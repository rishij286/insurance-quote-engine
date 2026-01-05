package com.rishi.quote_engine;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI quoteEngineApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Quote Engine API")
                        .description("Explainable pricing / quote service (age, region, risk score).")
                        .version("v1.0"));
    }
}