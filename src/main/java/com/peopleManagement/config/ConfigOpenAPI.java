package com.peopleManagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigOpenAPI {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de gerenciamento de pessoas com/e endereços")
                        .version("v1")
                        .description("API REST com CRUD para gerenciamento de pessoas e endereços, pessoas podem ter mais de um endereço mas somente um principal.")
                        .termsOfService("Termos de serviço")
                        .license(new License().name("MIT License").url(" https://opensource.org/licenses/MIT"))
                        .contact(new Contact().name("maxjdev").email("maxjramosdev@gmail.com").url("https://www.linkedin.com/in/maxjdev/"))
                );
    }
}

