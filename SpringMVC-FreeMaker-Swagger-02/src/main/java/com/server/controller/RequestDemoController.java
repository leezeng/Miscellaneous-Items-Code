package com.server.controller;

import com.server.common.CommonUtils;
import com.server.vo.PeopleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * 用来测试接收数据
 *
 * @author CYX
 * @create 2018-06-12-21:53
 */
@Controller
public class RequestDemoController {

    private static final Logger logger = LoggerFactory.getLogger(RequestDemoController.class);

    @Autowired
    private CommonUtils commonUtils;

    /**
     * RequestBody接受的是一个json对象的字符串，而不是一个json对象
     *
     * @param peopleVO
     * @param response
     */
    @RequestMapping(value = "/test_requestBody_1", method = RequestMethod.POST)
    public void test_requestBody_1(@RequestBody PeopleVO peopleVO, HttpServletResponse response) {
        System.out.println();
        System.out.println();
        logger.info("test_requestBody_1 : " + peopleVO.toString());
        commonUtils.flushResultToPage(response, "9529");
        System.out.println();
        System.out.println();
    }

    @RequestMapping(value = "/test_requestBody_2", method = RequestMethod.GET)
    public void test_requestBody_2(PeopleVO peopleVO, HttpServletResponse response) {
        System.out.println();
        System.out.println();
        logger.info("test_requestBody_2 : " + peopleVO.toString());
        commonUtils.flushResultToPage(response, "9530");
        System.out.println();
        System.out.println();
    }

    @RequestMapping(value = "/test_requestBody_3", method = RequestMethod.GET)
    public void test_requestBody_3(String userName, String userAge, String userAddress, HttpServletResponse response) {
        System.out.println();
        System.out.println();
        logger.info("test_requestBody_3");
        logger.info("userName : " + userName);
        logger.info("userAge : " + userAge);
        logger.info("userAddress : " + userAddress);
        commonUtils.flushResultToPage(response, "9531");
        System.out.println();
        System.out.println();
    }

    @RequestMapping(value = "/test_requestBody_4", method = RequestMethod.POST)
    public void test_requestBody_4(String data, HttpServletResponse response) {
        System.out.println();
        System.out.println();
        logger.info("data : " + data);
        commonUtils.flushResultToPage(response, "9532");
        System.out.println();
        System.out.println();
    }

}
