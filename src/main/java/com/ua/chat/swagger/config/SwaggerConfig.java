package com.ua.chat.swagger.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  /**
   * @return a Swagger Docket instance that controls which APIs will be documented.
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(false) //Disable auto generation of http status codes for each api end point
        .enableUrlTemplating(false)
        .select()
        .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
        .build()
        .apiInfo(metaData())
        .forCodeGeneration(true);
  }


  private ApiInfo metaData() {
    return new ApiInfoBuilder()
        .title("Chat")
        .description("Chat rest API")
        .build();
  }
}
