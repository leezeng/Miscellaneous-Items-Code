package com.server.spring.spring04;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 类CDPlayerConfig通过Java代码定义了Spring的装配规则。
 * <p>
 * 在后面我们再详细介绍
 * <p>
 * 这里，我们只需要观察一下CDPlayerConfig类并没有显示的声明任何bean。
 * <p>
 * 只不过它使用了@ComponentScan注解，这个注解能够在Spring中启用组件扫描。
 * <p>
 * 如果没有其他配置的话，@ComponentScan默认会扫描与配置类相同的包。
 * <p>
 * Spring将会扫描这个包以及这个包下所有的子包，查找带有@Component注解的类。
 *
 * 这种方式，我们暂时注释掉，使用XML来开启组件扫描。
 *
 * @author CYX
 * @create 2018-04-17-20:57
 */
@Configuration
@ComponentScan(basePackages = "com.server.spring.spring04")
public class CDPlayerConfig {
}
