package com.jwell.classifiedProtection;

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
public class Swagger2 {

    //    // 定义分隔符,配置Swagger多包
    private static final String CONTROLLER_BASE_PATH = "com.jwell.classifiedProtection.controller";

    private static final String CONTROLLER_APP_PATH = CONTROLLER_BASE_PATH + ".app";
    private static final String CONTROLLER_BACKEND_PATH = CONTROLLER_BASE_PATH + ".backend";
    private static final String CONTROLLER_COMMON_PATH = CONTROLLER_BASE_PATH + ".common";


    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     *
     * @return
     */
    @Bean
    public Docket restAppApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("前端接口")
                .apiInfo(apiInfo("【前台接口】Spring Boot中使用Swagger2构建RESTful APIs", "1.0"))
                .useDefaultResponseMessages(true)
                .forCodeGeneration(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage(CONTROLLER_APP_PATH))
//                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.regex("/app.*"))
                //.paths(PathSelectors.ant("/common.*"))
                .build();
    }

    @Bean
    public Docket restBackendApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("后台接口")
                .apiInfo(apiInfo("【后台接口】Spring Boot中使用Swagger2构建RESTful APIs", "1.0"))
                .select()
                .apis(RequestHandlerSelectors.basePackage(CONTROLLER_BACKEND_PATH))
                .paths(PathSelectors.regex("/backend.*"))
                //.paths(PathSelectors.ant("/common.*"))
                .build();
    }

    @Bean
    public Docket restCommonApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("通用接口")
                .apiInfo(apiInfo("【通用接口】Spring Boot中使用Swagger2构建RESTful APIs", "1.0"))
                .select()
                .apis(RequestHandlerSelectors.basePackage(CONTROLLER_COMMON_PATH))
                .paths(PathSelectors.regex("/common.*"))
                //.paths(PathSelectors.ant("/common.*"))
                .build();
    }


    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://ip:port/swagger-ui.html
     *
     * @return
     */
    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfoBuilder()
                .title(title)
                .description("更多Spring Boot相关文章请关注：http://blog.didispace.com/")
                .termsOfServiceUrl("http://www.jwell.com")
                .contact(new Contact("Ronnie", "http://www.jwell.com", "xulijun137@163.com"))
                .version(version)
                .build();
    }

}