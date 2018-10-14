package com.server.spring.spring10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author CYX
 * @create 2018-05-03-22:15
 */
@Component("userController")
public class UserController {

    @Autowired
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void handler() {
        System.out.println(userDao.addUser());
    }

}
