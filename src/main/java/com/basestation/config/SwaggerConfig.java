package com.basestation.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Created by me on 2020/5/1.
 */
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("demo")
                .apiInfo(getApiInfo())
                .select()
                //设置basePackage会将包下的所有被@Api标记类的所有方法作为api
                .apis(RequestHandlerSelectors.basePackage("com.mall.controller"))
                //只有标记了@ApiOperation的方法才会暴露出给swagger
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.regex("/api/.*")).build();
    }

    private ApiInfo getApiInfo(){

        return new ApiInfoBuilder()
                .title("基站管理系统API接口文档")
                .description("beta")
                // .termsOfServiceUrl("http://localhost/swagger-ui.html")
                .version("1.0")
                //.contact(new Contact("Kiana", "http://localhost/swagger-ui.html", "1428370636@qq.com"))
                .build();
    }

}
