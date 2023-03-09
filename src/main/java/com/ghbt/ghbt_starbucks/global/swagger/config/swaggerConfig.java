package com.ghbt.ghbt_starbucks.global.swagger.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
    type = SecuritySchemeType.HTTP,
    name = "basicAuth",
    scheme = "bearer")
public class swaggerConfig {

  @Bean
  public GroupedOpenApi jwtApi() {
    return GroupedOpenApi.builder()
        .group("jwt-api")
        .pathsToMatch("/**")
        .build();
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(new Info().title("Spring Boot API Example")
            .description("Spring Boot API 예시 프로젝트입니다.")
            .version("v0.0.1"));
  }
}
