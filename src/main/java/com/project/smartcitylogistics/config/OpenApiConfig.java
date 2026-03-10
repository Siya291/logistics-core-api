package com.project.smartcitylogistics.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Smart City Logistics API")
                        .version("1.0")
                        .description("Multi-tenant API for spatial courier tracking and logistics."))
                .components(new Components()
                        .addParameters("TenantHeader", new Parameter()
                                .in("header")
                                .name("X-TenantID")
                                .description("The vendor schema identifier")
                                .required(true)));
    }
}