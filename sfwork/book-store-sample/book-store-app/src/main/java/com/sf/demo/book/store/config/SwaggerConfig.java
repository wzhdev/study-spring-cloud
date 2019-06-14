package com.sf.demo.book.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //swagger要扫描的包路径
                .apis(RequestHandlerSelectors.basePackage("com.sf.demo.book.store.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Book Store Sample book-store-app API Doc")
                .description("Book Store Sample book-store-app, just for study!")
                .termsOfServiceUrl("http://localhost:8080/book-store-app")
                .contact(new Contact("book-store-app","http://localhost:8080/book-store-app/swagger-ui.html","ZhengheWang@sf-express.com"))
                .version("1.0")
                .build();
    }
}
