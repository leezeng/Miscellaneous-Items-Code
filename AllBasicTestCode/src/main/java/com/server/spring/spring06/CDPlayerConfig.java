package com.server.spring.spring06;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author CYX
 * @create 2018-04-17-20:57
 */
@Configuration
@ComponentScan(basePackages = "com.server.spring.spring06")
public class CDPlayerConfig {

    @Bean(name = "sssgtPeppers")
    public SgtPeppers initializationSgtPeppers() {
        return new SgtPeppers("888888", "99999");
    }

    @Bean
    public SgtPeppers initializationSgtPeppersWithHello(SayHello sayHello) {
        return new SgtPeppers(sayHello);
    }

    @Bean
    public SayHello initializationSayHello() {
        return new SayHello();
    }

}
