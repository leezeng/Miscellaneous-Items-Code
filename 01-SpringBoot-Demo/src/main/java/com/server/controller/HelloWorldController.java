package com.server.controller;

import com.server.vo.CardVO;
import com.server.vo.PersonVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloWorld-Controller-测试
 *
 * @author CYX
 * @create 2018-07-01-20:09
 */
@RestController
public class HelloWorldController {

    public static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldController.class);

    @Autowired
    private PersonVO personVO;

    @Autowired
    private CardVO cardVO;

    @RequestMapping(value = "/sayHello", method = RequestMethod.GET)
    public String sayHello() {

        LOGGER.info("-----");
        LOGGER.info("personVO : " + personVO.toString());
        LOGGER.info("cardVO : " + cardVO.toString());

        return "Hello Spring Boot , personVO : " + personVO.toString() + " , cardVO : " + cardVO.toString();
    }

}
