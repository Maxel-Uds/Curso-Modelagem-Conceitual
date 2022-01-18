package com.maxel.cursomc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.maxel.cursomc.resources"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "API do Curso de Springboot Com Ionic",
                "Esta API é utilizada no curso de Spring Boot com Ionic do prof. Nelio Alves",
                "Versão 1.0",
                "https://www.udemy.com/terms",
                new Contact("Maxel Udson", "https://github.com/Maxel-Uds", "maxellopes32@gmail.com"),
                "Permitido uso para estudantes",
                "https://www.udemy.com/terms",
                Collections.emptyList() // Vendor Extensions
        );
    }
}
