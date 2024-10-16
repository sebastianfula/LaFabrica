package com.uptc.fabrica.lafabricaapp.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("La Fábrica")
                        .description("Este proyecto consiste en una aplicación para gestionar una fábrica. El objetivo principal es permitir operaciones CRUD para cada entidad, así como su aplicación mediante un entorno Docker que conecta la aplicación Java con un servicio de MySQL.")
                        .version("v1.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación Adicional - Acceso al repositorio")
                        .url("https://github.com/sebastianfula/LaFabrica?tab=readme-ov-file"));
    }
}
