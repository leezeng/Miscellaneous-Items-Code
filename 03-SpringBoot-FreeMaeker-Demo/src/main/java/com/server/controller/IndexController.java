package com.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试Controller
 *
 * @author CYX
 * @create 2018-10-15-23:07
 */
@Controller
@RequestMapping(value = "/index")
public class IndexController {

    @RequestMapping(value = "/home")
    public ModelAndView index(ModelAndView modelAndView) {

        modelAndView.setViewName("index");
        
        List<String> userList = new ArrayList<String>();
        userList.add("admin");
        userList.add("user1");
        userList.add("user2");
        userList.add("user3");

        modelAndView.addObject("userList", userList);

        return modelAndView;
    }

}
