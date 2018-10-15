package com.server.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Swagger初始化配置文件
 *
 * @author CYX
 * @create 2018-10-15-20:54
 */
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"com.server.controller"})
@EnableWebMvc
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket customDocket() {
        //
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {

        Contact contact = new Contact("CCF-购物车", "https://www.baidu.com/", "ccf@xxx.com");

        return new ApiInfo("CCF-API开放接口",//大标题 title
                "CCF-Swagger-Demo",//小标题
                "0.0.0",//版本
                "https://www.baidu.com/",//服务条款网址
                contact,//作者
                "CCF",//链接显示文字
                "https://www.baidu.com/"//链接内容
        );
    }

}
