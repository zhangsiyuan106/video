package com.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @Description: 添加了Swagger的配置
 * @Param:
 * @author Dawn
 * @return
 * @date 2021/5/11 20:45
 */
@Configuration
@EnableSwagger2 //开启Swagger2
public class SwaggerConfig {

    @Bean
    public Docket docket1(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //名称
                .groupName("zsy")
                .select()
                //要扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.boot.controller.zsycontroller"))
                .build();
    }
    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("dawn")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.boot.controller.myqcontroller"))
                .build();
    }
    @Bean
    public Docket docket3(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("lm")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.boot.controller.lmcontroller"))
                .build();
    }

    @Bean
    public Docket docket4(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("hsw")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.boot.controller"))
                .build();
    }

    //配置Swagger 信息
    private ApiInfo apiInfo(){
        //作者信息
        Contact contact = new Contact("向前冲小组", "", "");

        return new ApiInfo(
                "Api Documentation" //标题名
                , "Api Documentation" //描述信息
                , "1.0"
                , "urn:tos"
                , contact
                , "Apache 2.0"
                , "http://www.apache.org/licenses/LICENSE-2.0"
                , new ArrayList());
    }
}
